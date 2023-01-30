package com.example.springboot.app.auth.filter;


import com.example.springboot.app.auth.service.JwtService;
import com.example.springboot.app.auth.service.impl.JwtServiceImpl;
import com.example.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    //Al ser una clase de filtro no se puede inyectar, por lo que se pasa por constructor
    private JwtService jwtService;


    public JwtAuthFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));

        this.jwtService = jwtService;
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
        //obtengo el token creado de la clase service
        String token = jwtService.create(authResult);
        response.addHeader(JwtServiceImpl.HEADER_STRING,JwtServiceImpl.TOKEN_PREFIX+token);

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
