package com.project.splitwise.controller;

import com.project.splitwise.dto.ConsolidatedSettlementDTO;
import com.project.splitwise.dto.SettleUpDTO;
import com.project.splitwise.service.GroupService;
import com.project.splitwise.service.SettlementService;
import com.project.splitwise.service.UserService;
import com.project.splitwise.strategies.SettleUpStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/settle")
public class SettlementController {

    @Autowired
    private SettleUpStrategy settleUpStrategy;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private SettlementService settlementService;

    @GetMapping("/group/{inputGroupName}")
    public ResponseEntity<?> getSettledUpGroup(@PathVariable String inputGroupName){
        try {
            Map<String, BigDecimal> userShareOfGroup = new HashMap<>();
            userShareOfGroup = groupService.findShareOfUsers(inputGroupName , userShareOfGroup);

            List<SettleUpDTO> output = settleUpStrategy.settleUpUsingHeap(userShareOfGroup);

            return new ResponseEntity<>(output,HttpStatus.OK);
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

    @GetMapping("/getAllSettled/user/{inputUserName}")
    public ResponseEntity<?> getSettledUpAllGroupsOfUser(@PathVariable String inputUserName){
        try {
            List<ConsolidatedSettlementDTO> output = settlementService.getSettledUpAllGroupsOfUser(inputUserName);

            return new ResponseEntity<>(output,HttpStatus.OK);
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
