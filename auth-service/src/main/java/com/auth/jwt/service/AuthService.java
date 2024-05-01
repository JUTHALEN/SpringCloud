package com.auth.jwt.service;

import com.auth.jwt.dto.AuthUserDto;
import com.auth.jwt.dto.NewUserDto;
import com.auth.jwt.dto.RequestDto;
import com.auth.jwt.dto.TokenDto;
import com.auth.jwt.entity.AuthUser;

public interface AuthService {
    
    boolean validateRequest(String token, RequestDto requestDto);
    
    AuthUser save(NewUserDto dto);
    
    TokenDto login(AuthUserDto dto);
    
    String validateToken(String token, RequestDto requestDto);
}
