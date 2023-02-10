package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.modelo.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> buscar();

    Cliente buscar(Long id);

    Cliente alta(Cliente cliente);

    Cliente modificar(Cliente cliente,Long id);

    void eliminar(Long id);
}
