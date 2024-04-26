package com.msvc.user.service;

import java.util.List;

import com.msvc.user.entity.User;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(String userId);
}
