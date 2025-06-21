package com.project.splitwise.controller;

import com.project.splitwise.dto.CreateExpenseDto;
import com.project.splitwise.entity.*;
import com.project.splitwise.repository.ExpenseRepository;
import com.project.splitwise.service.ExpenseService;
import com.project.splitwise.service.ExpenseShareService;
import com.project.splitwise.service.GroupService;
import com.project.splitwise.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/expense")
@Slf4j
public class ExpenseController {
	 
	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private ExpenseRepository expenseRepository;
	
	@PostMapping
    @Transactional
	public ResponseEntity<String> createExpense(@RequestBody CreateExpenseDto dto) {
		try {
	        expenseService.saveExpense(dto);
	        return ResponseEntity.ok("Expense created successfully");
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating expense: " + e.getMessage());
	    }
    }

	
}
