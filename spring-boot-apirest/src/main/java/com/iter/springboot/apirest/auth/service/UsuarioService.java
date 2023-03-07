package com.iter.springboot.apirest.auth.service;

import com.iter.springboot.apirest.modelo.Usuario;

public interface UsuarioService {

    Usuario buscar(String username);
}
