package com.project.splitwise.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.Date;

public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private Date date;
    private String description;

    @ManyToOne
    private User payer;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private Group group;

    // Constructors, getters, setters
}
