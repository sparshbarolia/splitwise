package com.project.splitwise.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
//establishes ManyToMany connection between user and group
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Group group;

    // Additional fields if needed (e.g., join date, role in group)

    // Constructors, getters, setters

}
