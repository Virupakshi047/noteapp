package com.software.noteapp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // converts your secret string into a cryptographic key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // generate token — takes username, returns JWT string
    public String generateToken(String username,String role,String email) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role",role)// who this token is for
                .claim("email",email)
                .setIssuedAt(new Date())        // when it was created
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())      // sign with your secret
                .compact();                     // build it into a string
    }

    // extract username from token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();                 // getSubject() = what you put in setSubject()
    }

    public String extractEmailId(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        return claims.get("email", String.class);
    }

    // validate token — is signature valid? is it expired?
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);    // throws exception if invalid or expired
            return true;
        } catch (Exception e) {
            return false;                      // any exception = invalid token
        }
    }
}
