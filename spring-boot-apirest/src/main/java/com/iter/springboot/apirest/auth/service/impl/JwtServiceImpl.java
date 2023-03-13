package com.iter.springboot.apirest.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iter.springboot.apirest.auth.SimpleGrantedAuthoritiesMixin;
import com.iter.springboot.apirest.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import static com.iter.springboot.apirest.parametros.parametros.*;

@Component
@Slf4j
public class JwtServiceImpl implements JwtService {


    @Override
    public String create(Authentication authentication) throws JsonProcessingException {

        //Creamos una colección de roles que vengan el autenticación proviniente del JWT
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        //Instanciamos los claims, los claims sirven para definir que vamos a meter dentro del JWT
        Claims claims = Jwts.claims();

        //Seconvierten los roles a formato Json para que sean reconocidos en el front y llenamos los claims
        claims.put("authorities",new ObjectMapper().writeValueAsString(roles));

        //Creamos el token
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
        try{
                getClaims(token);
                return true;
        }catch (JwtException | IllegalArgumentException e){
            log.error("error Aquiiiiiii");
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
    public String resolveToken(String token) {
        if(token!=null && token.startsWith(TOKEN_PREFIX)){
            return token.replace(TOKEN_PREFIX,"");
        }
        return null;
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


}
