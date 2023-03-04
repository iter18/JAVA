package com.iter.springboot.apirest.auth.filter;

import com.iter.springboot.apirest.auth.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.iter.springboot.apirest.parametros.parametros.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    //Filtro para saver si tiene el token en casda petición hacia el backend
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //Obtenemos la autización del header
        String header = request.getHeader(HEADER_STRING);

        //Validación del token para saber si existe en la cabecera
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

    //Método para saber si en el header la autorización es tipo Beaerer
    protected boolean requiresAthentication(String header){
        if(header == null || !header.startsWith(TOKEN_PREFIX)){
            return false;
        }
        return true;
    }
}
