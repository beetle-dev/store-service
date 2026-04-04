package com.cafe.storeservice.domain;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 200)
    private String address;

    @Column(length = 20)
    private String phone;

    private LocalTime openTime;
    private LocalTime closeTime;

    @Column(nullable = false)
    private boolean isActive = true;
}
