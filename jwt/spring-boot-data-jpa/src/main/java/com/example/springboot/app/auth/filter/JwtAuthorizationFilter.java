package com.example.springboot.app.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header  = request.getHeader("Authorization");

        if(!requiresAthentication(header)){
            chain.doFilter(request,response);
            return;
        }
        boolean validoToken;
        Claims token = null;

        try {
                token = Jwts.parserBuilder()
                    .setSigningKey(JwtAuthFilter.SECRET_KEY)
                    .build()
                    .parseClaimsJwt(header.replace("Bearer ",""))
                        .getBody();

                validoToken = true;
        }catch (JwtException | IllegalArgumentException e){
            validoToken = false;
        }
        if(validoToken){

        }
    }

    protected boolean requiresAthentication(String header){
        if(header == null || !header.startsWith("Bearer ")){
            return false;
        }
        return true;
    }
}
