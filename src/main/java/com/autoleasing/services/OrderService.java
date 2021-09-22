package com.autoleasing.services;

import com.autoleasing.dto.OrderDto;
import com.autoleasing.entity.Order;
import com.autoleasing.entity.User;
import com.autoleasing.enums.OrderStatus;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.FileIsNotUploadedException;
import com.autoleasing.exception.ValidationException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    void bookACar(Integer carId, Integer userId, String commentFromUser, LocalDate dateOfRent, LocalDate dateOfRentFinish) throws EntityNotFoundException, ValidationException;

    void updateOrder(OrderDto orderDto) throws EntityNotFoundException, ValidationException;

    List<Order> getAllOrderList();

    OrderDto getOrderById(Integer orderId) throws ValidationException, EntityNotFoundException;

    List<Order> getAllOrdersByUser(User user);

    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

    void endOfRent(MultipartFile fileFromView, Integer orderId) throws ValidationException, EntityNotFoundException, IOException, MessagingException, FileIsNotUploadedException;
}
