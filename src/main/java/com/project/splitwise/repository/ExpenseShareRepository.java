package com.project.splitwise.repository;

import com.project.splitwise.entity.ExpenseShare;
import com.project.splitwise.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, Long> {
}
