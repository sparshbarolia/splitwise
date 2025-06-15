package com.project.splitwise.service;

import com.project.splitwise.dto.SettleUpDTO;
import com.project.splitwise.dto.UserDto;
import com.project.splitwise.entity.User;
import com.project.splitwise.repository.UserRepository;
import com.project.splitwise.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private SettleUpStrategy settleUpStrategy;

    public User saveUser(User input){
        User savedUser = userRepository.save(input);

        return savedUser;
    }

    public Optional<User> findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public List<String> fetchGroupListForUser(String userName){
        Optional<User> currUser = userRepository.findByUserName(userName);
        if(currUser.isEmpty())throw new IllegalArgumentException("Please enter valid userName");

        List<String> output = new UserDto(currUser.get()).getGroups();

        return output;
    }

    public List<SettleUpDTO> fetchUsersSettledBalancesOfAllGroups(String userName){
        Map<String, BigDecimal> userWiseExpenseMap = new HashMap<>();

        //fetch all group names
        List<String> allGroups = fetchGroupListForUser(userName);

        //get all users friends total balances
        for(String groupName : allGroups){
            userWiseExpenseMap = groupService.findShareOfUsers(groupName,userWiseExpenseMap);
        }

        //settle up userWiseExpenseMap
        List<SettleUpDTO> output = settleUpStrategy.settleUpUsingHeap(userWiseExpenseMap);

        return output;
    }

    public Map<String,BigDecimal> fetchUsersFriendsBalancesOfAllGroups(String userName){
        Map<String, BigDecimal> userWiseExpenseMap = new HashMap<>();

        //fetch all group names
        List<String> allGroups = fetchGroupListForUser(userName);

        //get all users friends total balances
        for(String groupName : allGroups){
            userWiseExpenseMap = groupService.findShareOfUsers(groupName,userWiseExpenseMap);
        }

        return userWiseExpenseMap;
    }

}
