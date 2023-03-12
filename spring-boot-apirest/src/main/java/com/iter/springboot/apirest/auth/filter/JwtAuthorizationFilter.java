package com.iter.springboot.apirest.auth.filter;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.iter.springboot.apirest.auth.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.iter.springboot.apirest.parametros.parametros.*;

@Slf4j
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
       try {

               //Obtenemos la autización del header
               String header = request.getHeader(HEADER_STRING);

               //Validación del token para saber si existe en la cabecera
               if (!requiresAthentication(header)) {
                   chain.doFilter(request, response);
                   return;
               }
               //Validamos si el token es correcto
               UsernamePasswordAuthenticationToken authentication = null;
               if (jwtService.validate(header)) {

                   authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));
               }
               //Se asignan al request del usuario la autenticación  dentro del contexto
               if(authentication!=null){
                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }else{
                   throw new SignatureException("Sessión caduca");
               }

               chain.doFilter(request, response);
       }catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e){
           log.info("errrrrrorrrr"+ e.getClass().getSimpleName());
           JSONObject json = new JSONObject();
           Date date = new Date();
           Long timeError = date.getTime();
           json.put("timestamp",timeError);
           json.put("status",500);
           json.put("error","Internal Server Error");
           json.put("exception", "io.jsonwebtoken."+e.getClass().getSimpleName());
           json.put("message",e.toString());
           json.put("path",request.getRequestURI());
           response.setContentType("application/son");
           response.getWriter().print(json);
           response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
       }
    }

    //Método para saber si en el header la autorización es tipo Beaerer
    protected boolean requiresAthentication(String header){
        if(header == null || !header.startsWith(TOKEN_PREFIX)){
            return false;
        }
        return true;
    }
}
