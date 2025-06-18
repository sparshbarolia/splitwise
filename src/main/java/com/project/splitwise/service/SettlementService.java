package com.project.splitwise.service;

import com.project.splitwise.dto.ConsolidatedSettlementDTO;
import com.project.splitwise.dto.ConsolidatedSettlementMiniDTO;
import com.project.splitwise.dto.SettleUpDTO;
import com.project.splitwise.dto.SettlementShareDTO;
import com.project.splitwise.entity.Group;
import com.project.splitwise.repository.SettlementRepository;
import com.project.splitwise.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SettlementService {

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private SettleUpStrategy settleUpStrategy;

    public List<ConsolidatedSettlementDTO> getSettledUpAllGroupsOfUser(String inputUserName){

        //Map< friendName , ConsolidatedSettlementDTO >
        //to map friend with the transaction in each group
        Map<String, ConsolidatedSettlementDTO> friendToTransactionMapping = new HashMap<>();

        //fetch all groups name of user
        List<String> groupList = userService.fetchGroupListForUser(inputUserName);


        for(String groupName : groupList){
            //first settle up all groups individually
            Map<String, BigDecimal> userWiseExpenseMap = new HashMap<>();
            userWiseExpenseMap = groupService.findShareOfUsers(groupName,userWiseExpenseMap);

            //settle up userWiseExpenseMap
            List<SettleUpDTO> settledTransactions = settleUpStrategy.settleUpUsingHeap(userWiseExpenseMap);

            //if in these settled up transactions,operate on transactions to which user belongs
            for(SettleUpDTO s : settledTransactions){
                //if user belongs to that transaction,include it
                if(s.getPaidFrom().equalsIgnoreCase(inputUserName) || s.getPaidTo().equalsIgnoreCase(inputUserName)){
                    //get friend name
                    String friend = s.getPaidFrom();
                    if(!s.getPaidTo().equalsIgnoreCase(inputUserName)) friend = s.getPaidTo();

                    ConsolidatedSettlementMiniDTO tempMiniDTO = new ConsolidatedSettlementMiniDTO(s,groupName);

                    //if entry for friend already exists
                    if(friendToTransactionMapping.containsKey(friend)){
                        ConsolidatedSettlementDTO tempDTO = friendToTransactionMapping.get(friend);

                        if(tempMiniDTO.getPaidFrom().equalsIgnoreCase(inputUserName)){
                            tempDTO.setTotalAmount(tempDTO.getTotalAmount().subtract(tempMiniDTO.getAmount()));
                        }
                        else tempDTO.setTotalAmount(tempDTO.getTotalAmount().add(tempMiniDTO.getAmount()));

                        tempDTO.getIndividualGroupShare().add(tempMiniDTO);
                    }
                    else{
                        ConsolidatedSettlementDTO tempDTO;
                        if(tempMiniDTO.getPaidFrom().equalsIgnoreCase(inputUserName)){
                            tempDTO = new ConsolidatedSettlementDTO(tempMiniDTO.getAmount().negate());
                        }
                        else tempDTO = new ConsolidatedSettlementDTO(tempMiniDTO.getAmount());

                        tempDTO.getIndividualGroupShare().add(tempMiniDTO);

                        friendToTransactionMapping.put(friend,tempDTO);
                    }

                }
            }
        }

        List<ConsolidatedSettlementDTO> output = new ArrayList<>();

        for (Map.Entry<String, ConsolidatedSettlementDTO> entry : friendToTransactionMapping.entrySet()) {
            ConsolidatedSettlementDTO tempDTO = entry.getValue();

            if(tempDTO.getTotalAmount().compareTo(BigDecimal.ZERO) > 0){
                tempDTO.setPaidTo(inputUserName);
                tempDTO.setPaidFrom(entry.getKey());
            }
            else if(tempDTO.getTotalAmount().compareTo(BigDecimal.ZERO) < 0){
                tempDTO.setPaidTo(entry.getKey());
                tempDTO.setPaidFrom(inputUserName);
            }

            output.add(tempDTO);
        }

        return output;
    }
}
