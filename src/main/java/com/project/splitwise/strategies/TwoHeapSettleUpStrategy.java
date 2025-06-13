package com.project.splitwise.strategies;

import com.project.splitwise.dto.SettlementShareDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TwoHeapSettleUpStrategy implements SettleUpStrategy{

    @Override
    public List<SettlementShareDTO> settleUpUsingHeap(){
        List<SettlementShareDTO> output = new ArrayList<SettlementShareDTO>();
        return output;
    }
}
