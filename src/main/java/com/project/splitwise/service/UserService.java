package com.project.splitwise.service;

import com.project.splitwise.entity.User;
import com.project.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User input){
        User savedUser = userRepository.save(input);

        return savedUser;
    }

    public Optional<User> findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
