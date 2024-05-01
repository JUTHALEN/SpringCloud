package com.auth.jwt.service.impl;

import com.auth.jwt.dto.AuthUserDto;
import com.auth.jwt.dto.NewUserDto;
import com.auth.jwt.dto.RequestDto;
import com.auth.jwt.dto.TokenDto;
import com.auth.jwt.entity.AuthUser;
import com.auth.jwt.repository.AuthUserRepository;
import com.auth.jwt.security.JwtProvider;
import com.auth.jwt.security.RouteValidate;
import com.auth.jwt.service.AuthService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    AuthUserRepository authUserRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    RouteValidate routeValidate;
    
    @Override
    public boolean validateRequest(String token, RequestDto requestDto) {
        Claims claims = jwtProvider.parseToken(token);
        if(jwtProvider.validateClaims(claims)) {
            String role = (String) claims.get("role");
            if (routeValidate.isAdmin(requestDto)) {
                return "admin".equals(role);
            }
            return true;
        }
        return false;
    }
    
    @Override
    public AuthUser save(NewUserDto dto) {
        
        Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());
        if(user.isPresent()) {
            return null;
        }
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder().username(dto.getUsername()).password(password).role(dto.getRole()).build();
        return authUserRepository.save(authUser);
    }
    
    @Override
    public TokenDto login(AuthUserDto dto) {
        
        Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());
        if(user.isEmpty()) {
            return null;
        }
        if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
            String token = jwtProvider.generateJwtToken(String.valueOf(user.get().getId()));
            Claims claims = jwtProvider.parseToken(token);
            if(jwtProvider.validateClaims(claims)) {
                return new TokenDto(token);
            }
        }
        return null;
    }
    
    @Override
    public String validateToken(String token, RequestDto requestDto) {
        Claims claims = jwtProvider.parseToken(token);
        if(jwtProvider.validateClaims(claims)) {
            return jwtProvider.getSubject(claims);
        }
        return null;
    }
}
