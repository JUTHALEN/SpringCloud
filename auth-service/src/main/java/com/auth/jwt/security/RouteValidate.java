package com.auth.jwt.security;

import com.auth.jwt.dto.RequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidate {
    
    private List<RequestDto> paths;
    
    public boolean isAdmin(RequestDto requestDto) {
        return paths.stream().anyMatch(p -> Pattern.matches(p.getUri(), requestDto.getUri()) && p.getMethod()
               .equals(requestDto.getMethod()));
    }
}
