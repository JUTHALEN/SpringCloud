package com.msvc.user.controller;

import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msvc.user.entity.User;
import com.msvc.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @PostMapping
    ResponseEntity<User> saveUser (@RequestBody User userRequest){
        User user = userService.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users =  userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    int count = 0;
    
    @GetMapping("id/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelRetry", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        log.info("Count {} ", count);
        count++;
        User user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception exception){
        
        log.info("Fallback method called {} ", exception.getMessage());
        User user = User.builder()
                .userId("1234")
                .name("root")
                .email("user1@gmail.com")
                .information("User information - service down")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
