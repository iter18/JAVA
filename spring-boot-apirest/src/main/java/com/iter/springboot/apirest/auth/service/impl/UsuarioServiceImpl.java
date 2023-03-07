package com.iter.springboot.apirest.auth.service.impl;

import com.iter.springboot.apirest.auth.repository.Specification.UsuarioSpecification;
import com.iter.springboot.apirest.auth.repository.UsuarioRepository;
import com.iter.springboot.apirest.auth.service.UsuarioService;
import com.iter.springboot.apirest.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario buscar(String username) {
        try {
            Specification<Usuario> filtro = UsuarioSpecification.username(username);
            Usuario usuario = usuarioRepository.findOne(filtro).orElse(null);
            return usuario;
        }catch (Exception e){
            throw e;
        }
    }
}
