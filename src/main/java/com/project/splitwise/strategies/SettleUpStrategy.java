package com.project.splitwise.strategies;

import com.project.splitwise.dto.SettleUpDTO;
import com.project.splitwise.dto.SettlementShareDTO;

import java.util.List;

public interface SettleUpStrategy {
    List<SettleUpDTO> settleUpUsingHeap(String inputGroupName);
}
