package com.example.springboot.app.dao.services;


import com.example.springboot.app.models.entity.Usuario;

public interface UsuarioService {

    Usuario buscar(String username);
}
