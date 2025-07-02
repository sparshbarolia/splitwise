package com.project.splitwise.controller;

import com.project.splitwise.entity.User;
import com.project.splitwise.service.UserDetailsServiceImplementation;
import com.project.splitwise.service.UserService;
import com.project.splitwise.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementaion;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    //jwt token generate krdega if userName and pass sahi hue to
    @PostMapping("/login")  //user creation public rehna chahiye
    public ResponseEntity<String> login(@RequestBody User newUser){
        try {
            //to authenticate user using UserDetailsServiceImplementaion and spring security
            //agr userName,password match nhi hua to yahi pr error ajaega and catch me chle jaenge
            //spring security me authenticationManagerBean() function bnaya h iske liye
            // /public end point secure nhi h isliye alg se verification krni pdi hume user ki yaha
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newUser.getUserName() , newUser.getPassword()));
            //authenticated user ki details lakr dedo
            UserDetails userDetails = userDetailsServiceImplementaion.loadUserByUsername(newUser.getUserName());
            //generate token
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt , HttpStatus.OK);
        }
        catch (Exception e){
            log.error("exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect userName or password",HttpStatus.NOT_FOUND);
        }
    }
}
