package com.iter.springboot.apirest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig  {


    //Version de spring 2.7 en adelante ya no hereda de WebSecurityConfigurerAdapter por lo que ahora las configuraciones son por medio de beans
    //securityFilterChain sustituyo a configurer, aquí va toda la seguridad, de espring
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // by default uses a Bean by the name of corsConfigurationSource
                .cors(withDefaults());
        return http.build();
    }

    //Bean que se encarga de configurar de forma global el origen a acceso a nuestro end point
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*","http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin","Access-Control-Allow-Methods","Access-Control-Allow-Headers","Access-Control-Max-Age"
                ,"Access-Control-Request-Headers","Access-Control-Request-Method","Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
