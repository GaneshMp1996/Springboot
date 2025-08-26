package com.project.service;

import com.project.exceptionhandler.OrderNotFoundException;
import com.project.orders.entity.Order;
import com.project.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        logger.info("Received request to create order with idempotencyKey={}", order.getIdempotencyKey());
        Optional<Order> existingOrder = orderRepository.findByIdempotencyKey(order.getIdempotencyKey());

        if (existingOrder.isPresent()) {
            throw new OrderNotFoundException.DuplicateIdempotencyKeyException(
                    "Order already exists with Idempotency-Key: " + order.getIdempotencyKey()
            );
        } else {
            logger.info("No existing order found with idempotencyKey={}. Creating a new order.", order.getIdempotencyKey());
            return orderRepository.save(order);
        }
    }

    public List<Order> getAllOrders() {
        List<Order> getallOrders = orderRepository.findAll();
        if (getallOrders != null) {
            logger.info("Retrived all the oder details. ", getallOrders);
            return getallOrders;
        } else {
            logger.info("No order details found in the DB");
            if (getallOrders.isEmpty()) {
                throw new OrderNotFoundException("No orders found in the database");
            }
            return getallOrders;

        }
    }

    public Optional<Order> getOrderById(long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order;
    }

}

