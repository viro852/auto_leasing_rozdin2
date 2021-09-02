package com.autoleasing.controllers;

import com.autoleasing.components.UserComponent;
import com.autoleasing.dto.CarDto;
import com.autoleasing.dto.CreateOrderRequest;
import com.autoleasing.dto.OrderDto;
import com.autoleasing.entity.Car;
import com.autoleasing.entity.Order;
import com.autoleasing.entity.Role;
import com.autoleasing.enums.OrderStatus;
import com.autoleasing.enums.RoleEnum;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    private final SendEmailService sendEmailService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final UserComponent userComponent;
    private final CarService carService;
    private final CarConverter carConverter;
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Autowired
    public OrderController(UserComponent userComponent, CarService carService, CarConverter carConverter, OrderService orderService, SendEmailService sendEmailService, OrderConverter orderConverter) {
        this.userComponent = userComponent;
        this.carService = carService;
        this.carConverter = carConverter;
        this.orderService = orderService;
        this.sendEmailService = sendEmailService;
        this.orderConverter = orderConverter;
    }

    @GetMapping("/orderList")
    public String orderList(Model model) {
        model.addAttribute("orderList", orderService.getAllOrderList());
        Role admin = userComponent.getUser().getSetOfRoles().stream().filter(elem -> elem.getRoleEnum().equals(RoleEnum.ADMIN)).findAny().orElseGet(() -> {
            return new Role();
        });
        model.addAttribute("admin", admin.getRoleEnum() == RoleEnum.ADMIN);

        List<Order> userOrders = orderService.getAllOrdersByUser(userComponent.getUser());
        model.addAttribute("userOrders", userOrders);
        return "orderList";
    }

    @GetMapping("/newOrderForm")
    public String newOrderForm(@RequestParam("carId") String carId, Model model) {

        logger.info("attempt to book car with id:" + carId);

        Integer carIdFromView = Integer.parseInt(carId);

        Car car = null;
        try {
            car = carConverter.fromCarDtoToCar(carService.getCarById(carIdFromView));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();

        model.addAttribute("car", car.getId());
        model.addAttribute("carModel", car.getModel());
        model.addAttribute("order", createOrderRequest);
        model.addAttribute("user", userComponent.getUser().getId());
        return "newOrderForm";
    }

    @PostMapping("/createOrder")
    public String createOrder(@ModelAttribute("order") CreateOrderRequest orderRequest, Model model) {
        logger.info("attemt to make an order from user: " + orderRequest.getUserId());

        try {
            orderService.bookACar(orderRequest.getCarId(), orderRequest.getUserId(), orderRequest.getComment(), orderRequest.getStart(), orderRequest.getFinish());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            model.addAttribute(orderRequest.getCarId());
            return "errorWhenSaveNewOrder";
        }

        CarDto carDto = null;
        try {
            carDto = carService.getCarById(orderRequest.getCarId());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        model.addAttribute("bookedCarBrand", carDto.getBrand());
        model.addAttribute("bookedCarModel", carDto.getModel());

        model.addAttribute("dateOfStart", orderRequest.getStart());
        model.addAttribute("dateOfFinish", orderRequest.getFinish());
        return "successOrder";
    }

    @PostMapping("/acceptOrder")
    public String acceptOrder(@RequestParam("orderId") int orderId) throws ValidationException, EntityNotFoundException {

        OrderDto orderToUpdate = orderService.getOrderById(orderId);
        orderToUpdate.setOrderStatus(OrderStatus.ACCEPTED);
        orderService.updateOrder(orderToUpdate);
        return "redirect:/orderList";
    }

    @PostMapping("/declineOrder")
    public String declineOrder(@RequestParam("orderIdForDecline") int orderIdForDecline, @RequestParam(name = "declineCommentary") String declineCommentary) throws ValidationException, EntityNotFoundException {
        OrderDto orderToDecline = orderService.getOrderById(orderIdForDecline);
        orderToDecline.setAdminCommentary(declineCommentary);
        orderToDecline.setOrderStatus(OrderStatus.DECLINED);
        orderService.updateOrder(orderToDecline);
        return "redirect:/orderList";
    }

    @GetMapping("/declineOrder")
    public String getDeclineOrderPage(@RequestParam("orderIdForDecline") int orderIdForDecline, Model model) throws ValidationException, EntityNotFoundException {
        model.addAttribute("orderIdForDecline", orderIdForDecline);
        return "declineOrderPage";
    }

    @GetMapping("/activeOrders")
    public String getAllActiveOrders(Model model) {
        List<Order> activeOrderList = orderService.findAllByOrderStatus(OrderStatus.ACCEPTED);
        model.addAttribute("activeOrderList", activeOrderList);
        return "activeOrderList";
    }

    @GetMapping("/closeOrderForm")
    public String getCloseOrderForm(@RequestParam(name = "orderId") int orderId, Model model) {
        model.addAttribute("orderToBeClosed", orderId);
        return "closeOrderForm";
    }

    @PostMapping("/closeOrder")
    public String CloseOrder(@RequestParam(name = "condition") String condition, @RequestParam(name = "orderId") int orderId, @RequestParam(name = "comment") String commentary) throws ValidationException, EntityNotFoundException {
        OrderDto order = orderService.getOrderById(orderId);
        if (condition.equals("not damaged")) {
            order.setOrderStatus(OrderStatus.SUCCESS);
            orderService.updateOrder(order);
        } else {
            order.setAdminCommentary(commentary);
            order.setOrderStatus(OrderStatus.FAIL);
            orderService.updateOrder(order);
        }
        return "redirect:/activeOrders";
    }

    @PostMapping("/endOfRent")
    public String attemptToEndRent(@RequestParam(name = "orderId") int orderId) throws ValidationException, EntityNotFoundException {
        OrderDto order = orderService.getOrderById(orderId);
        order.setOrderStatus(OrderStatus.ENDREQUEST);
        orderService.updateOrder(order);


        return "redirect:/orderList";
    }

}
