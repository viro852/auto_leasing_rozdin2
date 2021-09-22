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
import com.autoleasing.exception.FileIsNotUploadedException;
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
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Controller
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final UserComponent userComponent;
    private final CarService carService;
    private final CarConverter carConverter;
    private final OrderService orderService;
    private final UsersConverter usersConverter;

    @Autowired
    public OrderController(UserComponent userComponent, CarService carService, CarConverter carConverter, OrderService orderService, UsersConverter usersConverter) {
        this.userComponent = userComponent;
        this.carService = carService;
        this.carConverter = carConverter;
        this.orderService = orderService;
        this.usersConverter = usersConverter;
    }

    @GetMapping("/orderList")
    public String orderList(Model model) {
        model.addAttribute("orderList", orderService.getAllOrderList());
        model.addAttribute("currentUser",usersConverter.fromUserToUserDto(userComponent.getUser()));
        Role admin = userComponent.getUser().getSetOfRoles().stream().filter(elem -> elem.getRoleEnum().equals(RoleEnum.ADMIN)).findAny().orElseGet(() -> {
            return new Role();
        });
        model.addAttribute("admin", admin.getRoleEnum() == RoleEnum.ADMIN);

        List<Order> userOrders = orderService.getAllOrdersByUser(userComponent.getUser());
        model.addAttribute("userOrders", userOrders);
        return "orderList";
    }

    @GetMapping("/newOrderForm")
    public String newOrderForm(@RequestParam("carId") Integer carId, Model model) {
        logger.info("attempt to book car with id:" + carId);

        Car car = null;
        try {
            car = carConverter.fromCarDtoToCar(carService.getCarById(carId));
        } catch (EntityNotFoundException e) {
            logger.error("We have error: " + e.getMessage());
            model.addAttribute("reason","Auto is unavilable now");
            return "errorPage";
        } catch (ValidationException e) {
            logger.error("We have error: " + e.getMessage());
            return "errorPage";
        }

        if (!car.getAvailable()) {
            return "errorWhenCarIsUnavailable";
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
            logger.error("We have error: " + e.getMessage());
        } catch (ValidationException e) {
            model.addAttribute(orderRequest.getCarId());
            return "errorWhenSaveNewOrder";
        }

        CarDto carDto = null;
        try {
            carDto = carService.getCarById(orderRequest.getCarId());
        } catch (EntityNotFoundException e) {
            logger.error("We have error: " + e.getMessage());
            model.addAttribute("reason","Auto is unavilable now");
            return "errorPage";
        } catch (ValidationException e) {
            logger.error("We have error: " + e.getMessage());
            return "errorPage";
        }

        model.addAttribute("bookedCarBrand", carDto.getBrand());
        model.addAttribute("bookedCarModel", carDto.getModel());

        model.addAttribute("dateOfStart", orderRequest.getStart());
        model.addAttribute("dateOfFinish", orderRequest.getFinish());
        return "successOrder";
    }

    @PostMapping("/acceptOrder")
    public String acceptOrder(@RequestParam("orderId") Integer orderId) throws ValidationException, EntityNotFoundException {
        OrderDto orderToUpdate = orderService.getOrderById(orderId);
        orderToUpdate.setOrderStatus(OrderStatus.ACCEPTED);
        orderService.updateOrder(orderToUpdate);
        return "redirect:/orderList";
    }

    @PostMapping("/declineOrder")
    public String declineOrder(@RequestParam("orderIdForDecline") Integer orderIdForDecline, @RequestParam(name = "declineCommentary") String declineCommentary) throws ValidationException, EntityNotFoundException {
        OrderDto orderToDecline = orderService.getOrderById(orderIdForDecline);
        orderToDecline.setAdminCommentary(declineCommentary);
        orderToDecline.setOrderStatus(OrderStatus.DECLINED);
        orderService.updateOrder(orderToDecline);
        return "redirect:/orderList";
    }

    @GetMapping("/declineOrder")
    public String getDeclineOrderPage(@RequestParam("orderIdForDecline") Integer orderIdForDecline, Model model) throws ValidationException, EntityNotFoundException {
        logger.info("attempt to decline order with id: " + orderIdForDecline);
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
    public String getCloseOrderForm(@RequestParam(name = "orderId") Integer orderId, Model model) {
        logger.info("attempt to close order with id: " + orderId);
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

    @GetMapping("/endOfRentForm")
    public String getEndRentForm(@RequestParam(name = "orderId") Integer orderId, Model model) throws ValidationException, EntityNotFoundException {
        logger.info("attempt to end order with id: " + orderId);
        model.addAttribute("orderId", orderId);
        return "endOfRentForm";
    }

    @PostMapping("/endOfRent")
    public String attemptToEndRent(@RequestParam(name = "orderId") Integer orderId, @RequestParam(name = "image") MultipartFile multipartFile, Model model) throws ValidationException, EntityNotFoundException {
        try {
            orderService.endOfRent(multipartFile,orderId);
        } catch (IOException e) {
            logger.error("We have error: " + e.getMessage() + ".User redirected to orderList page");
            return "redirect:/orderList";
        } catch (MessagingException e) {
            logger.error("We have error: " + e.getMessage() + ".User redirected to orderList page");
            return "redirect:/orderList";
        } catch (FileIsNotUploadedException e) {
            model.addAttribute("orderId", orderId);
            logger.error("We have error: " + e.getMessage() + ".User redirected to errorWhenFileIsNotUploaded page");
            return "errorWhenFileIsNotUploaded";
        }
        return "redirect:/orderList";
    }
}
