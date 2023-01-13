package com.example.springboot.app.auth.filter;

import com.example.springboot.app.auth.SimpleGrantedAuthoritiesMixin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //Este filtro se ejecuta en todas las peticiones
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header  = request.getHeader("Authorization");
        //La validación del token solo se hace si existe en la cabecera de la petición
        if(!requiresAthentication(header)){
            chain.doFilter(request,response);
            return;
        }
        boolean validoToken;
        Claims token = null;

        try {
            logger.info("---->Llave secreta generada authorization: "+JwtAuthFilter.SECRET_KEY.getEncoded().toString());
                token = Jwts.parserBuilder()
                    .setSigningKey(JwtAuthFilter.SECRET_KEY)
                    .build()
                    .parseClaimsJws(header.replace("Bearer ",""))
                        .getBody();

                validoToken = true;
        }catch (JwtException | IllegalArgumentException e){
            validoToken = false;
            e.printStackTrace();
        }
        //proceso para validar token
        UsernamePasswordAuthenticationToken authentication = null;
        if(validoToken){
            String username = token.getSubject();
            Object roles = token.get("authorities");
            //Convertimos el json de roles  a una colección
            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesMixin.class)
                    .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
            authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);
        }
        //Se asignan al request del usuario la autenticación  dentro del contexto
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    protected boolean requiresAthentication(String header){
        if(header == null || !header.startsWith("Bearer ")){
            return false;
        }
        return true;
    }
}
