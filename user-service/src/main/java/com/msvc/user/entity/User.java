package com.msvc.user.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    String userId;

    @Column(name = "nome")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "information")
    String information;

    @Transient
    List<Qualification> qualifications = new ArrayList<>();
}
