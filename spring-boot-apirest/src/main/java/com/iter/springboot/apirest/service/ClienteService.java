package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.dtos.ClienteDto;
import com.iter.springboot.apirest.modelo.Cliente;

import java.util.HashMap;
import java.util.List;

public interface ClienteService {

    List<ClienteDto> buscar();

    ClienteDto buscar(Long id);

    ClienteDto alta(ClienteDto cliente);
    Cliente altaHM(HashMap<String,?> cliente);

    ClienteDto modificar(ClienteDto cliente,Long id);

    void eliminar(Long id);
}
