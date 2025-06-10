package com.project.splitwise.service;

import com.project.splitwise.entity.Expense;
import com.project.splitwise.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense){
        return expenseRepository.save(expense);
    }
}
