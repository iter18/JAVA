package com.iter.springboot.apirest.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

public interface JwtService {

    //Método que creará el token
    String create(Authentication authentication) throws JsonProcessingException;

    //Método para validar token
    boolean validate(String token);

    //Método para obtener los claims del token
    Claims getClaims(String token);

    //Método para procesar o resolver, decodificar el token
    String resolveToken(String token);

    //Método para obtener el username
    String getUsername(String token);

    //Método para obtener los roles
    Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
}
