package com.example.springboot.app.auth.service.impl;

import com.example.springboot.app.auth.SimpleGrantedAuthoritiesMixin;
import com.example.springboot.app.auth.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtServiceImpl implements JwtService {

    //Definición de constantes para proceso JWT
    //Constante para llave secreta
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    //Constante para fecha expiración
    public static final long EXPIRATION_DATE = 3600000;
    //Constante para prefijo del token
    public static final String TOKEN_PREFIX = "Bearer ";
    //Constante con tipo de autorización en el HEADER
    public static final String HEADER_STRING = "Authorization";


    @Override
    public String create(Authentication authentication) throws JsonProcessingException {

        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        Claims claims = Jwts.claims();
        //Seconvierten los roles a formato Json
        claims.put("authorities",new ObjectMapper().writeValueAsString(roles));
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .signWith(SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_DATE))
                .compact();
        return token;
    }

    @Override
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(resolveToken(token))
                .getBody();
        return claims;
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
        return authorities;
    }

    @Override
    public String resolveToken(String token) {
        if(token!=null && token.startsWith(TOKEN_PREFIX)){
            return token.replace(TOKEN_PREFIX,"");
        }
        return null;
    }
}
