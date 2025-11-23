package com.paypal.user_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;


@Component
public class JWTUtil {
    private static final String SECRET="secret123secret123";

    //key to signedKey in bytes conversion
    public Key getSignedKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
    //get email from token
    public String extractEmail(String token){
        return Jwts.parserBuilder().setSigningKey(getSignedKey())
                .build()
                .parseClaimsJwt(token).getBody().getSubject();
    }
    public boolean validateToken(String token,String username){
        try {
            extractEmail(token);//if parsing succeds token is valid
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String extractUserName(String token){
        return Jwts.parserBuilder().setSigningKey(getSignedKey()).build()
                .parseClaimsJwt(token).getBody().getSubject();//username-email
    }

    public String generateToken(Map<String,Object> claims,String email){
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 3600000)).signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
