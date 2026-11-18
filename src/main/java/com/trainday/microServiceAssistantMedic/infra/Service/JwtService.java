package com.trainday.microServiceAssistantMedic.infra.Service;


import com.trainday.microServiceAssistantMedic.Domain.Enum.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;


    private Key getKey() {

        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Key testgetKey() {

        return getKey();
    }

    public String generateToken(
            String email,
            String crm,
            String professionalId, Role role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("crm", crm);
        claims.put("email", email);
        claims.put("professionalId", professionalId);
        claims.put("role", role.name());
        System.out.println("Subject = " + crm);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(crm)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}




