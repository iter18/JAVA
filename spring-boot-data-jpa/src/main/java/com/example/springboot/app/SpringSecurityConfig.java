package com.example.springboot.app;

import com.example.springboot.app.auth.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
/** La anotación @EnableGlobalMethodSecurity es para habilitar la anotación @Secured y @PreAuthorize
 * Despues se debe anotar en cada método del controller con el nombre del rol que tendrá el permiso del recurso
 * y si un controller sera para un rol en especifico se deve colocara la anotación hasta arriba, @secured **/

@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/cliente/", "/css/**", "/js/**", "/images/**", "/cliente/listar")
                .permitAll()
                /* Esta forma es para dar permisos de forma manual sabiendo que roles vamos a esperar
                .antMatchers("/cliente/ver/**").hasAnyRole("USER")
                .antMatchers("/uploads/**").hasAnyRole("USER")
                .antMatchers("/cliente/form/**").hasAnyRole("ADMIN")
                .antMatchers("/cliente/eliminar/**").hasAnyRole("ADMIN")
                .antMatchers("/facturaOptimo/**").hasAnyRole("ADMIN")*/
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successHandler).loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");
    }

    @Autowired

    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception{
        /*De momento el método será tener los usuarios en memoria*/
        /*PasswordEncoder encoder = passwordEncoder;
        User.UserBuilder users = User.builder().passwordEncoder(encoder::encode);
        builder.inMemoryAuthentication()
                .withUser(users.username("admin")
                        .password("123456")
                        .roles("ADMIN","USER"))
                .withUser(users.username("andres")
                        .password("123456")
                        .roles("USER"));*/

        //Esta es la manera correcta mediante DB pero usando una consulta SQL
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username,password,enabled from users where username=?")
                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");

    }

}
