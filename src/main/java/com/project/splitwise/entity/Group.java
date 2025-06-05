package com.project.splitwise.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<UserGroup> users = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();

    // Constructors, getters, setters

}
