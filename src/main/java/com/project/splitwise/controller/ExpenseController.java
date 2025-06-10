package com.project.splitwise.controller;

import com.project.splitwise.entity.*;
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
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ExpenseShareService expenseShareService;

    @Transactional
    @PostMapping("/percentage/user/userName/group/groupName")
    public ResponseEntity<?> addExpenseByPercentage(
            @RequestBody Expense inputExpense,
            @RequestBody ExpenseUserDivision inputExpenseUserDivision,
            @PathVariable String userName,
            @PathVariable String groupName
    ){
        try {
            //check added percentage is correct or not
            Double percentageSum = 0.0;
            for(Double value : inputExpenseUserDivision.getUserPercentages().values()){
                percentageSum += value;
            }
            if(percentageSum != 100)throw new IllegalArgumentException("please enter valid percentages");

            //find user
            Optional<User> currPayer = userService.findByUserName(userName);
            if(currPayer.isEmpty())throw new NullPointerException("No such user exist with given userName");

            //find group
            Optional<Group> currGroup = groupService.findByGroupName(groupName);
            if(currGroup.isEmpty())throw new NullPointerException("No such group exist with given groupName");

            //create expense
            Date currentDate = new Date();
            inputExpense.setDate(currentDate);
            inputExpense.setStatus(ExpenseStatus.PENDING);
            inputExpense.setPayer(currPayer.get());
            inputExpense.setGroup(currGroup.get());

            Expense currExpense = expenseService.saveExpense(inputExpense);

            //create expenseShares
            inputExpenseUserDivision.getUserPercentages().forEach((key,value) -> {
                Optional<User> tempUser = userService.findByUserName(key);

                BigDecimal currUserExpenseShare = (BigDecimal.valueOf(value * 0.01)).multiply(currExpense.getAmount());
                ShareStatus currStatus = ShareStatus.OWES;
                if(tempUser.get().getUserName().equals(currPayer.get().getUserName())) currStatus = ShareStatus.PAID;

                ExpenseShare currExpenseShare = new ExpenseShare(currUserExpenseShare,BigDecimal.valueOf(0),currStatus,tempUser.get(),currExpense);

                ExpenseShare currSavedExpenseShare = expenseShareService.saveExpense(currExpenseShare);
                currExpense.getShares().add(currSavedExpenseShare);
            });

            //save expense again to commit latest changes
            expenseService.saveExpense(currExpense);

            return new ResponseEntity<>("Expense saved successfully!",HttpStatus.OK);
        }
        catch (Exception e){
            log.error("error in creating group",e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "message", e.getMessage()
                    ));
        }
    }
}
