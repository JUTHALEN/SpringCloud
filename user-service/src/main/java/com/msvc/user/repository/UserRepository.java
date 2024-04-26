package com.msvc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msvc.user.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
