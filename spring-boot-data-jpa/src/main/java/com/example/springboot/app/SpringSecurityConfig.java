package com.example.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/cliente/", "/css/**", "/js/**", "/images/**", "/cliente/listar")
                .permitAll()
                .antMatchers("/cliente/ver/**").hasAnyRole("USER")
                .antMatchers("/uploads/**").hasAnyRole("USER")
                .antMatchers("/cliente/form/**").hasAnyRole("ADMIN")
                .antMatchers("/cliente/eliminar/**").hasAnyRole("ADMIN")
                .antMatchers("/facturaOptimo/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");
    }

    @Autowired
    /*De momento el método será tener los usuarios en memoria*/
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception{
        PasswordEncoder encoder = passwordEncoder();
        User.UserBuilder users = User.builder().passwordEncoder(encoder::encode);
        builder.inMemoryAuthentication()
                .withUser(users.username("admin")
                        .password("123456")
                        .roles("ADMIN","USER"))
                .withUser(users.username("andres")
                        .password("123456")
                        .roles("USER"));
    }

}
