package com.project.splitwise.controller;

import com.project.splitwise.dto.SettleUpDTO;
import com.project.splitwise.dto.UserDto;
import com.project.splitwise.entity.User;
import com.project.splitwise.service.GroupService;
import com.project.splitwise.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName){
        try {
            if(userName.length() == 0){
                throw new IllegalArgumentException("userName can't be blank");
            }
            Optional<User> fetchedUser = userService.findByUserName(userName);

            if(fetchedUser.isEmpty())throw new IllegalArgumentException("Please enter valid userName");

            UserDto userDto = new UserDto(fetchedUser.get());

            return new ResponseEntity<>(userDto,HttpStatus.OK);
        }
        catch (Exception e){
            log.error("error in creating user",e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "message", e.getMessage()
                    ));
        }
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User inputUser){
        try {
            if(inputUser.getUserName().length() == 0 || inputUser.getEmail().length() == 0){
                throw new IllegalArgumentException("userName or Email can't be blank");
            }
            User savedUser = userService.saveUser(inputUser);

            return new ResponseEntity<>(savedUser , HttpStatus.CREATED);
        }
        catch (Exception e){
            log.error("error in creating user",e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/getBalance/all/{userName}")
    public ResponseEntity<?> fetchUsersFriendsBalancesOfAllGroups(@PathVariable String userName){
        try {
            Map<String,BigDecimal> userWiseExpenseMap = userService.fetchUsersFriendsBalancesOfAllGroups(userName);

            return new ResponseEntity<>(userWiseExpenseMap,HttpStatus.OK);
        }
        catch (Exception e){
            log.error("error in creating user",e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/getBalance/all/settled/{userName}")
    public ResponseEntity<?> fetchUsersSettledBalancesOfAllGroups(@PathVariable String userName){
        try {
            List<SettleUpDTO> allSettledUpTransactions = userService.fetchUsersSettledBalancesOfAllGroups(userName);

            return new ResponseEntity<>(allSettledUpTransactions,HttpStatus.OK);
        }
        catch (Exception e){
            log.error("error in creating user",e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "message", e.getMessage()
                    ));
        }
    }
}
