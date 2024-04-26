package com.msvc.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msvc.user.entity.Qualification;
import com.msvc.user.entity.User;
import com.msvc.user.excepcion.ResourceNotFoundException;
import com.msvc.user.repository.UserRepository;
import com.msvc.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        User user= userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found, with id: " + userId));
        ArrayList<Qualification> qualificationByUser = restTemplate.getForObject("http://localhost:8083/api/qualifications/user/" + user.getUserId(), ArrayList.class);
        logger.info("Qualifications: " + qualificationByUser);

        user.setQualifications(qualificationByUser);
        return user;
    }
}
