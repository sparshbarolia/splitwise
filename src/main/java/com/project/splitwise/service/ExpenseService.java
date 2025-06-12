package com.project.splitwise.service;

import com.project.splitwise.dto.CreateExpenseDto;
import com.project.splitwise.dto.ParticipantDto;
import com.project.splitwise.entity.Expense;
import com.project.splitwise.entity.ExpenseShare;
import com.project.splitwise.entity.ExpenseStatus;
import com.project.splitwise.entity.Group;
import com.project.splitwise.entity.ShareStatus;
import com.project.splitwise.entity.User;
import com.project.splitwise.repository.ExpenseRepository;
import com.project.splitwise.repository.ExpenseShareRepository;
import com.project.splitwise.repository.GroupRepository;
import com.project.splitwise.repository.UserRepository;
import com.project.splitwise.service.splitstrategy.SplitStrategy;
import com.project.splitwise.service.splitstrategy.SplitStrategyFactory;import jakarta.persistence.SharedCacheMode;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseService {
    
	@Autowired
    private UserRepository userRepository;
    
	@Autowired
    private GroupRepository groupRepository;
	
    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private ExpenseShareRepository expenseShareRepository;

    public void saveExpense(CreateExpenseDto dto){
       
    	// Fetch user and group
        User user = userRepository.findById(dto.getCreatedByUserId()).orElseThrow();
        Group group = groupRepository.findById(dto.getGroupId()).orElseThrow();
        
        //Create Expense
        Expense expense = new Expense();
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getTotalAmount());
        expense.setDate(new java.util.Date());
        expense.setPayer(user);
        expense.setGroup(group);
        expense.setSplitType(dto.getSplitType());
        expense.setStatus(ExpenseStatus.PENDING);
        
        expense = expenseRepository.save(expense);

        // Use split strategy
        SplitStrategy strategy = SplitStrategyFactory.getStrategy(dto.getSplitType());
        Map<Long, BigDecimal> calculatedOwedAmounts = strategy.calculateShares(dto.getTotalAmount(), dto.getParticipants());

        //Save Participants
        for(ParticipantDto participantDto : dto.getParticipants())
        {
        	ExpenseShare participants = new ExpenseShare();
        	participants.setExpense(expense);
        	participants.setUser(userRepository.findById(participantDto.getUserId()).orElseThrow());
        	
        	BigDecimal paid = participantDto.getPaidAmount();
            BigDecimal owed = calculatedOwedAmounts.get(participantDto.getUserId());
            
            participants.setPaidAmount(paid);
            participants.setShareAmount(owed);
            
            if (paid.compareTo(owed) >= 0) {
                participants.setStatus(ShareStatus.PAID);
            } else if (paid.compareTo(BigDecimal.ZERO) == 0) {
                participants.setStatus(ShareStatus.OWES);
            } else {
                participants.setStatus(ShareStatus.PARTIALLY_PAID);
            }
            
            expenseShareRepository.save(participants);
        }
        

    	
    }
}
