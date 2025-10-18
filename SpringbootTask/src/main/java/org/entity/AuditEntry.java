package org.entity;

import java.time.Instant;
@Entity
public class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String actor;
    private String action;
    private String details;
    private Instant createdAt;
}
