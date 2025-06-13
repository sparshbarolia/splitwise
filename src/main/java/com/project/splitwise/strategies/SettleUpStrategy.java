package com.project.splitwise.strategies;

import com.project.splitwise.dto.SettlementShareDTO;

import java.util.List;

public interface SettleUpStrategy {
    List<SettlementShareDTO> settleUpUsingHeap();
}
