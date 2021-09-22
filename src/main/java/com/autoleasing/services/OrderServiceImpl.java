package com.autoleasing.services;

import com.autoleasing.dto.CarDto;
import com.autoleasing.dto.OrderDto;
import com.autoleasing.entity.Car;
import com.autoleasing.entity.Order;
import com.autoleasing.entity.User;
import com.autoleasing.enums.OrderStatus;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.FileIsNotUploadedException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.repository.OrderRepo;
import com.autoleasing.utils.FileUploadUtil;
import com.autoleasing.validation.CarIdValidation;
import com.autoleasing.validation.OrderIdValidation;
import com.autoleasing.validation.OrderValidation;
import com.autoleasing.validation.UserIdValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final CarService carService;
    private final UserService userService;
    private final OrderValidation orderValidation;
    private final UsersConverter usersConverter;
    private final CarConverter carConverter;
    private final CarIdValidation carIdValidation;
    private final UserIdValidation userIdValidation;
    private final OrderIdValidation orderIdValidation;
    private final OrderConverter orderConverter;
    private final SendEmailService sendEmailService;
    @Value("${upload.path.carcondition}")
    private String carConditionPath;


    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, CarService carService, UserService userService, OrderValidation orderValidation, UsersConverter usersConverter, CarConverter carConverter, CarIdValidation carIdValidation, UserIdValidation userIdValidation, OrderIdValidation orderIdValidation, OrderConverter orderConverter, SendEmailService sendEmailService) {
        this.orderRepo = orderRepo;
        this.carService = carService;
        this.userService = userService;
        this.orderValidation = orderValidation;
        this.usersConverter = usersConverter;
        this.carConverter = carConverter;
        this.carIdValidation = carIdValidation;
        this.userIdValidation = userIdValidation;
        this.orderIdValidation = orderIdValidation;
        this.orderConverter = orderConverter;
        this.sendEmailService = sendEmailService;
    }

    public void bookACar(Integer carId, Integer userId, String commentaryFromUser, LocalDate dateOfRent, LocalDate dateOfRentFinish) throws EntityNotFoundException, ValidationException {
        carIdValidation.carIdValidateMethod(carId);
        userIdValidation.userIdValidateMethod(userId);


        User user = usersConverter.fromUserDtoToUser(userService.getUserById(userId));
        CarDto carDto = carService.getCarById(carId);


        Order order = new Order();

        if (carDto.getAvailable()) {
            carDto.setAvailable(Boolean.FALSE);
        }
        carService.updateCar(carDto);

        Car car = carConverter.fromCarDtoToCar(carDto);

        order.setUser(user);
        order.setCar(car);
        order.setOrderStatus(OrderStatus.NEW);
        order.setDateOfRent(dateOfRent);
        order.setDateOfRentFinish(dateOfRentFinish);
        order.setCommentary(commentaryFromUser);
        orderValidation.validateOrderDto(orderConverter.fromOrderToOrderDto(order));

        orderRepo.save(order);
        CarDto orderedCar = carService.getCarById(carId);

        sendEmailService.sendEmail(sendEmailService.getAdminEmail(), "the customer ordered brand:" + orderedCar.getBrand() + " model:" + orderedCar.getModel() + "\n" + "DateOfStart:" + dateOfRent + " DateOfFinish:" + dateOfRentFinish + "\n" + "comment from user:" + commentaryFromUser, "New Order");
    }

    @Override
    public void updateOrder(OrderDto orderDto) throws EntityNotFoundException, ValidationException {
        Order orderFromDB = orderConverter.fromOrderDtoToOrder(orderDto);

        orderRepo.save(orderFromDB);

        switch (orderFromDB.getOrderStatus()) {
            case ACCEPTED:
                sendEmailService.sendEmail(orderFromDB.getUser().getEmail(), "Статус Вашей заявки на аренду автомобиля: " + orderFromDB.getOrderStatus() + ", Вы можете оплатить и забрать автомобиль в офисе", "Your order is approved");
                break;
            case DECLINED:
                sendEmailService.sendEmail(orderFromDB.getUser().getEmail(), "Статус Вашей заявки на аренду автомобиля: " + orderFromDB.getOrderStatus() + " Причина: " + orderFromDB.getAdminCommentary(), "Your order is declined");
                break;
            case SUCCESS:
                CarDto carDto = carConverter.fromCarToCarDto(orderDto.getCar());
                carDto.setAvailable(Boolean.TRUE);
                carService.updateCar(carDto);
                sendEmailService.sendEmail(orderFromDB.getUser().getEmail(), "Вы успешно сдали автомобиль: " + orderFromDB.getOrderStatus(), "Your order is successfully closed");
                break;
            case FAIL:
                sendEmailService.sendEmail(orderFromDB.getUser().getEmail(), "Статус Вашей заявки на аренду автомобиля: " + orderFromDB.getOrderStatus() + " Причина: " + orderFromDB.getAdminCommentary(), "Your order cant be closed");
                break;
            case ENDREQUEST:
                break;
        }
    }

    @Override
    public List<Order> getAllOrderList() {
        return orderRepo.findAll();
    }

    @Override
    public OrderDto getOrderById(Integer orderId) throws ValidationException, EntityNotFoundException {
        orderIdValidation.orderIdValidateMethod(orderId);
        Order orderFromDB = orderRepo.findById(orderId).orElseThrow(() -> {
            return new EntityNotFoundException("Order not found");
        });
        return orderConverter.fromOrderToOrderDto(orderFromDB);
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        List<Order> usersOrderList = orderRepo.findAllByUser(user);
        return usersOrderList;
    }

    @Override
    public List<Order> findAllByOrderStatus(OrderStatus orderStatus) {
        List<Order> activeOrderList = orderRepo.findAllByOrderStatus(orderStatus);
        return activeOrderList;
    }

    @Override
    public void endOfRent(MultipartFile multipartFile, Integer orderId) throws ValidationException, EntityNotFoundException, IOException, MessagingException, FileIsNotUploadedException {
        Order orderForEnd=orderRepo.findById(orderId).orElseThrow(()->{ return new EntityNotFoundException("Entity not found");});
        orderForEnd.setOrderStatus(OrderStatus.ENDREQUEST);
        OrderDto orderForEndDto=orderConverter.fromOrderToOrderDto(orderForEnd);

        String fileName = multipartFile.getOriginalFilename();
        String uploadDir = carConditionPath + orderId;
        String fileabsolutePath = carConditionPath + orderId + "//" + fileName;

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        updateOrder(orderForEndDto);
        sendEmailService.sendEmailWithAttachment("Attempt to end order " + orderForEndDto.getId() + " Пожалуйста, проверьте состояние автомобиля перед присвоением нового статуса", " End order request", fileabsolutePath);
    }
}



