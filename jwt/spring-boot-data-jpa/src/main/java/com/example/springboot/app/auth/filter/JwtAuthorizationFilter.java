package com.example.springboot.app.auth.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    }

    protected boolean requiresAthentication(String header){
        if(header == null || !header.startsWith("Bearer ")){
            return false;
        }
        return true;
    }
}
