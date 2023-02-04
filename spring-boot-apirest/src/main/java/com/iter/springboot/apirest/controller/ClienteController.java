package com.iter.springboot.apirest.controller;


import com.iter.springboot.apirest.modelo.Cliente;
import com.iter.springboot.apirest.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Se puede configurar los permisos de métodos permitidos al REST, GET,POST,DELETE,etc.
 * Si se deja sólamente con la dirección del dominio de donde se compartiran los recursos
 * por defecto tendra permiso a todos los métodos y las cabezeras
 */
//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@Slf4j
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.buscar();
    }
}
