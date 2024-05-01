package com.auth.jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    
    public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;
    
    @Value("${jwt.token.validityInSeconds:36000}")
    long validityInSeconds;
    
    byte[] signingKey;
    
    SecretKey secretKey;
    
    @Autowired
    RouteValidate routeValidate;
    
    @Autowired
    public void setJWTBase64Secret(@Value("${jwt.base64secret:Qn1c6is2IdhJxizxing+IvTm1ylG2COH" +
            "+cLGOGjtjJzs04B9IK38TnWAxOWqGrACh1IWDPMuCW8ECOJ80dL3kA}") final String jwtSecret) {
        
        signingKey = Decoders.BASE64.decode(jwtSecret);
        secretKey = Keys.hmacShaKeyFor(signingKey);
    }
    
    public String generateJwtToken(final String subject) {
        
        final Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + validityInSeconds * 1000)).signWith(secretKey)
                   .compact();
    }
    
    public Claims parseToken(final String token) {
        
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }
    
    public boolean validateClaims(final Claims claims) {
        
        return System.currentTimeMillis() < claims.getExpiration().getTime();
    }
    
    public String getSubject(final Claims claims) {
        
        return claims.getSubject();
    }
}

