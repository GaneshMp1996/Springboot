package com.project.orders.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = "idempotencyKey"))
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    private int quantity;

    private int price;

    private String location;

    private String idempotencyKey;  // Unique key to prevent duplicates

    // getters & setters
}

