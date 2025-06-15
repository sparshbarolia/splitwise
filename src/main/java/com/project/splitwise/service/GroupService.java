package com.project.splitwise.service;

import com.project.splitwise.dto.SettlementShareDTO;
import com.project.splitwise.entity.*;
import com.project.splitwise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group saveGroup(Group inputGroup){
        return groupRepository.save(inputGroup);
    }

    public Optional<Group> findByGroupName(String inputGroupName){
        return groupRepository.findByGroupName(inputGroupName);
    }

    public Map<String, BigDecimal> findShareOfUsers(String inputGroupName , Map<String,BigDecimal> userWiseExpenseMap){
        //find group
        Optional<Group> currGroup = findByGroupName(inputGroupName);

        if(currGroup.isEmpty())throw new IllegalArgumentException("please enter valid groupName");

//        Map<String,BigDecimal> userWiseExpenseMap = new HashMap<>();

        for(Expense currExpense : currGroup.get().getExpenses()){

            if(currExpense.getStatus() == ExpenseStatus.SETTLED)continue;

            for(ExpenseShare currExpenseShare : currExpense.getShares()){

                String currUserName = currExpenseShare.getUser().getUserName();;
                BigDecimal currBalanceAmount = currExpenseShare.getPaidAmount().subtract(currExpenseShare.getShareAmount());

                if (userWiseExpenseMap.containsKey(currUserName)) {
                    userWiseExpenseMap.put(currUserName, userWiseExpenseMap.get(currUserName).add(currBalanceAmount));
                } else {
                    userWiseExpenseMap.put(currUserName, currBalanceAmount);
                }
            }
        }
        return userWiseExpenseMap;
    }



}
