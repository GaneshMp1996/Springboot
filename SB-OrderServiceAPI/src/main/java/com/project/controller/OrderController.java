
package com.project.controller;

import com.project.exceptionhandler.OrderNotFoundException;
import com.project.orders.entity.Order;
import com.project.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/getorderbyid/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        return ResponseEntity.ok(order);
    }

    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            logger.info("No orders found in the database.");
            throw new OrderNotFoundException("No orders found in the database.");
        }
        logger.info("Retrieved all orders successfully.");
        return ResponseEntity.ok(orders);
    }
}
