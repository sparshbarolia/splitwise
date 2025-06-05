package com.project.splitwise.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserGroup> groups = new ArrayList<>();

    @OneToMany(mappedBy = "payer", cascade = CascadeType.ALL)
    private List<Expense> expensesPaid = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ExpenseShare> expenseShares = new ArrayList<>();

    // Constructors, getters, setters

}
