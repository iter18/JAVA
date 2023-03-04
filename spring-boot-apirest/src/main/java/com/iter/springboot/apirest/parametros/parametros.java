package com.iter.springboot.apirest.parametros;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class parametros {


    //Definición de constantes para proceso JWT
    //Constante para llave secreta
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    //Constante para fecha expiración
    public static final long EXPIRATION_DATE = 3600000;
    //Constante para prefijo del token
    public static final String TOKEN_PREFIX = "Bearer ";
    //Constante con tipo de autorización en el HEADER
    public static final String HEADER_STRING = "Authorization";
}
