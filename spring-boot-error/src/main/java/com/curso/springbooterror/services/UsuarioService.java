package com.curso.springbooterror.services;

import com.curso.springbooterror.models.domain.Usuario;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface UsuarioService {

    List<Usuario> listar();
    Usuario buscarPorId(Integer id);
}
