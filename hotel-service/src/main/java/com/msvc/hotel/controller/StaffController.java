package com.msvc.hotel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    
    @GetMapping
    public ResponseEntity<String> getStaff() {
        
        List<String> staff = List.of("John", "Doe", "Jane", "Julen");
        return new ResponseEntity<>(staff.toString(), HttpStatus.OK);
    }
}
