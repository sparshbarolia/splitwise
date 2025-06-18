package com.project.splitwise.repository;

import com.project.splitwise.entity.Expense;
import com.project.splitwise.entity.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
            select es
            from
            	ExpenseShare es
            where
            	es.user.userName = :userName and
            	es.status != 'PAID' and
            	es.expense.status != 'SETTLED' and
            	es.expense.group.groupName = :groupName
            """)
    List<ExpenseShare> findExpenseSharesOfUserInAGroup(
            @Param("userName") String userName,
            @Param("groupName") String groupName
    );
}
