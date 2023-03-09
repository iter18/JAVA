package com.iter.springboot.apirest.auth.filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iter.springboot.apirest.auth.service.JwtService;
import com.iter.springboot.apirest.modelo.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import static com.iter.springboot.apirest.parametros.parametros.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //Se instancian clases de autenticación y de servicio de JWT
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    //Constructor con clases para inyectar clases de JWT y Autenticación
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,  JwtService jwtService) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        //Especificamos que la autenticación sea por método post y por ruta especifica
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
        this.jwtService = jwtService;
    }

    //Método para validar la autentificación
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = null;
        String password = null;

        //Forma Basic Authentication
        String authorizationHeader = request.getHeader(HEADER_STRING);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            //Forma de hacerlo con un formdata
            username = this.obtainUsername(request);
            password = this.obtainPassword(request);
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
        }
        String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        String[] values = credentials.split(":", 2);

        username = values[0];
        password = values[1];
        username = username.trim();
        //Asignamos las credenciales al authenticationToken
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
        response.setContentType("application/json");
        response.addHeader(HEADER_STRING,TOKEN_PREFIX+token);

        Map<String,Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", authResult.getPrincipal() );
        /*data.put("mensaje",String.format("Hola %s, has iniciado sesión con éxito!",authResult.getName()));*/
        response.getWriter().write(new ObjectMapper().writeValueAsString(data));
        response.setStatus(200);


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
