package com.curso.springbooterror.services.impl;

import com.curso.springbooterror.models.domain.Usuario;
import com.curso.springbooterror.services.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private List<Usuario> lista;

    public UsuarioServiceImpl() {
        this.lista = new ArrayList<>();
        lista.add(new Usuario(1,"José","Ojeda"));
        lista.add(new Usuario(2,"Aarón","Bonifacio"));
        lista.add(new Usuario(3,"Viviana","Estrada"));
        lista.add(new Usuario(4,"Omar","Estrada"));
    }

    @Override
    public List<Usuario> listar() {
        return this.lista;
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        Usuario usuario = this.lista.stream().filter(u->u.getId().equals(id)).findFirst()
                .orElseThrow(()->new IllegalArgumentException("No existe usuario con este id"));
        return usuario;
    }
}
