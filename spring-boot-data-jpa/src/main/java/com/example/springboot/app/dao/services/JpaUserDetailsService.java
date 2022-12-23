package com.example.springboot.app.dao.services;

import com.example.springboot.app.models.entity.Rol;
import com.example.springboot.app.models.entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("jpaUserDetailsService")
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioService usuarioService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscar(username);

        if(usuario == null){
            log.error("Error: El usuario "+username+" no existe");
            throw new UsernameNotFoundException("El usuario no existe");
        }

        //Forma 1 de obtener roles por ciclo for Java < 8
        /*List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();
        for (Rol rol : usuario.getRoles()){
            authoritiesList.add(new SimpleGrantedAuthority(rol.getAuthority()));
        }*/

        //Forma 2 de obtener roles por Stream Java >8
        List<GrantedAuthority> authoritiesList = usuario.getRoles().stream().map(rol -> {
            SimpleGrantedAuthority s = new SimpleGrantedAuthority(rol.getAuthority());
            return s;
        }).collect(Collectors.toList());


        if(authoritiesList.isEmpty()){
            log.error("Error: El usuario "+username+" no tiene roles");
            throw new UsernameNotFoundException("El usuario no tiene roles asignados");
        }
        return new User(usuario.getUsername(),
                        usuario.getPassword(),
                        usuario.getEnabled(),
                        true,
                        true,
                        true,
                        authoritiesList);
    }
}
