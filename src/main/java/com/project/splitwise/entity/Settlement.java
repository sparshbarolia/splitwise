package com.project.splitwise.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
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
