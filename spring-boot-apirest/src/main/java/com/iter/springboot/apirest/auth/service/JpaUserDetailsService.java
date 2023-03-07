package com.iter.springboot.apirest.auth.service;


import com.iter.springboot.apirest.modelo.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
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
        //Obtenemos los roles asociados al usuario
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
