package com.msvc.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.msvc.user.dto.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msvc.user.dto.Qualification;
import com.msvc.user.entity.User;
import com.msvc.user.excepcion.ResourceNotFoundException;
import com.msvc.user.repository.UserRepository;
import com.msvc.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        
        final var user= userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found, with id: " + userId));
        
        final var qualificationByUser = restTemplate.getForObject("http://QUALIFICATION-SERVICE/api/qualifications/user/" + user.getUserId(), Qualification[].class);
        logger.info("{} ", (Object)qualificationByUser);
        final var qualifications = Arrays.stream(qualificationByUser).collect(Collectors.toCollection(ArrayList::new));
        
        final var listQualifications = qualifications.stream().peek(qualification -> {
           logger.info("Qualification: " + qualification.getHotelId());
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/api/hotel/" + qualification.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            qualification.setHotel(hotel);
            logger.info("Response with code status: {}", forEntity.getStatusCode());
        }).toList();
        user.setQualifications(listQualifications);
        return user;
    }
}
