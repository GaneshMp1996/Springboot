package com.project.commandline;

import com.project.orders.entity.Order;
import com.project.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    private final OrderService orderService;

    public StartupRunner(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application is starting... executing pre-start logic.");

        // Check if orders table is empty
        List<Order> existingOrders = orderService.getAllOrders();
        if (existingOrders.isEmpty()) {
            // Create a default order
            Order defaultOrder = new Order();
            defaultOrder.setProduct("Default Product");
            defaultOrder.setQuantity(1);
            defaultOrder.setPrice(0);
            defaultOrder.setLocation("Default Location");
            defaultOrder.setIdempotencyKey("startup-key");

            Order savedOrder = orderService.createOrder(defaultOrder);
            logger.info("Default order created at startup: {}", savedOrder);
        } else {
            logger.info("Orders table already has data, skipping default order creation.");
        }

        logger.info("Pre-start logic executed successfully.");
    }
}
