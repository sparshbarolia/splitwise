package com.project.splitwise.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class ExpenseShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal shareAmount;
    private BigDecimal paidAmount;
    private ShareStatus status; // OWES, PAID, etc.

    @ManyToOne
    private User user;

    @ManyToOne
    private Expense expense;

    // Constructors, getters, setters
}
