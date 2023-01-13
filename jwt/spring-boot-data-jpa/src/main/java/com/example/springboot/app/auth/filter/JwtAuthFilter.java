package com.example.springboot.app.auth.filter;


import com.example.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
    }
    //Método para validar autentificación
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);

        //Forma de hacerlo con un formdata
        if(username != null && password != null){
            logger.info("Username desde request parameter (form-data): "+ username);
            logger.info("password desde request parameter (form-data): "+ password);
        }else{
            //Si no se hace con form-data hay que recibirlo como json y convertimos a Json lo recibido
            Usuario user = null;
            try {
                 user= new ObjectMapper().readValue(request.getInputStream(),Usuario.class);
                 username = user.getUsername();
                 password = user.getPassword();
                logger.info("Username desde request parameter (raw): "+ username);
                logger.info("password desde request parameter (raw): "+ password);
            } catch (JsonParseException e) {
                e.printStackTrace();
            }catch (JsonMappingException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }

    // método que se ejecuta cuando la autenticación es exitosa
    ////// se crea el jwt con código secreto
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
       // SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String llaveSecreta = JwtAuthFilter.SECRET_KEY.getEncoded().toString();
        logger.info("---->Llave secreta generada: "+llaveSecreta);
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        Claims claims = Jwts.claims();
        //Seconvierten los roles a formato Json
        claims.put("authorities",new ObjectMapper().writeValueAsString(roles));
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(authResult.getName())
                .signWith(JwtAuthFilter.SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .compact();
        response.addHeader("Authorization","Bearer "+token);

        Map<String,Object> body = new HashMap<>();
        body.put("token", token);
        body.put("user", authResult.getPrincipal() );
        body.put("mensaje",String.format("Hola %s, has iniciado sesión con éxito!",authResult.getName()));
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        Map<String,Object> body = new HashMap<>();
        body.put("mensaje","Error de autenticación: username o password incorrecto!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}
