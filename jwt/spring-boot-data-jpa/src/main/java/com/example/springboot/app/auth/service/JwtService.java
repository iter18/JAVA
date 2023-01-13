package com.example.springboot.app.auth.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

/**
 * Clase de servicio que se encargara de crear, validar tokens ya sea con io.webtoken u otra alternativa para trabajar con JWT
 * */
public interface JwtService {

    //Método que creará el token
    public String create(Authentication authentication) throws JsonProcessingException;

    //Método para validar el token creado
    public  boolean validate(String token);

    //Método para obtener los claims = los datos que viajan por el token
    Claims getClaims(String token);

    //Método para obtener el username
    String getUsername(String token);

    //Método para obtener los roles
    Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;

    //Método para procesar o resolver, decodificar el token
    String resolveToken(String token);

}
