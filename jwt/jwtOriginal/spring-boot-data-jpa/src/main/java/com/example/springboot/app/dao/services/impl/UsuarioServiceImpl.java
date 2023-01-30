package com.example.springboot.app.dao.services.impl;

import com.example.springboot.app.dao.repositorys.UsuarioDao;
import com.example.springboot.app.dao.repositorys.spec.UsuarioSpecification;
import com.example.springboot.app.dao.services.UsuarioService;
import com.example.springboot.app.models.entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioDao usuarioDao;

    @Override
    public Usuario buscar(String username) {
        try {
                Specification<Usuario> filtro = UsuarioSpecification.username(username);
                Usuario usuario = usuarioDao.findOne(filtro).orElse(null);
                return usuario;
        }catch (Exception e){
            throw e;
        }
    }
}
