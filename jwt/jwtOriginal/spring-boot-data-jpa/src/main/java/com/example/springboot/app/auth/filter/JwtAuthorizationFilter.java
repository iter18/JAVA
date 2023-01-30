package com.example.springboot.app.auth.filter;

import com.example.springboot.app.auth.service.JwtService;
import com.example.springboot.app.auth.service.impl.JwtServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    //Al ser una clase de filtro no se puede inyectar, por lo que se pasa por constructor
    private JwtService jwtService;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    //Este filtro se ejecuta en todas las peticiones
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header  = request.getHeader(JwtServiceImpl.HEADER_STRING);
        //La validación del token solo se hace si existe en la cabecera de la petición
        if(!requiresAthentication(header)){
            chain.doFilter(request,response);
            return;
        }

        //Validamos si el token es correcto
        UsernamePasswordAuthenticationToken authentication = null;
        if(jwtService.validate(header)){

            authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header),null,jwtService.getRoles(header));
        }
        //Se asignan al request del usuario la autenticación  dentro del contexto
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    protected boolean requiresAthentication(String header){
        if(header == null || !header.startsWith(JwtServiceImpl.TOKEN_PREFIX)){
            return false;
        }
        return true;
    }
}
