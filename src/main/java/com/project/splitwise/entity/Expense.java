package com.project.splitwise.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;
    private Date date;
    private ExpenseStatus status; // PENDING, SETTLED,PARTIALLY_SETTLED

    @ManyToOne
    private User payer;

    @ManyToOne
    private Group group;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    private List<ExpenseShare> shares = new ArrayList<>();

    // Constructors, getters, setters

}
