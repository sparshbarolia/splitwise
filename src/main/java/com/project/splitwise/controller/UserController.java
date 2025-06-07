package com.project.splitwise.controller;

import com.project.splitwise.entity.User;
import com.project.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "message", e.getMessage()
                    ));
        }
    }
}
