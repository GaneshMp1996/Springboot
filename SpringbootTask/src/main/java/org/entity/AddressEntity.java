package org.entity;

import lombok.Data;

@Data
@Entity
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

