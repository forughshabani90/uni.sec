package com.uni.sec.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
@Component
public class JwtUtil {
    @Value("${spring.uni.app.jwtsecret}")
    private String jwtSecret;

    @Value("${spring.uni.app.jwtExpiration}")
    private String jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        UserDetails details = (User) authentication.getPrincipal();
        return Jwts.builder().
                setSubject(details.getUsername())
                .setIssuedAt(new Date()).
                setExpiration(new Date(new Date().getTime() + jwtExpiration)).
                signWith(key(), SignatureAlgorithm.HS256).compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().
                parseClaimsJwt(token).getBody().getSubject();
    }

    public boolean validationJwtToken(String token) {
       try {


           Jwts.parserBuilder().setSigningKey(key()).build()
                   .parse(token);
           return true;
       }
       catch (Exception e) {
           return false;
       }
    }
}
