package com.project.splitwise.service;

import com.project.splitwise.entity.ExpenseShare;
import com.project.splitwise.repository.ExpenseShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseShareService {

    @Autowired
    private ExpenseShareRepository expenseShareRepository;

    public ExpenseShare saveExpense(ExpenseShare expenseShare){
        return expenseShareRepository.save(expenseShare);
    }
}
