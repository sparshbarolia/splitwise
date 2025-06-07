package com.project.splitwise.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
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
