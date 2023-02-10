package com.iter.springboot.apirest.controller;


import com.iter.springboot.apirest.modelo.Cliente;
import com.iter.springboot.apirest.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/clientes/{id}")
    public Cliente buscar(@PathVariable Long id){
        return clienteService.buscar(id);
    }

    //Este método es una forma haciendo directamente con objetos, pero existe otro método más comercial
    @PostMapping("clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente alta(@RequestBody Cliente cliente){
        return clienteService.alta(cliente);
    }
    //Este método es una forma haciendo directamente con objetos, pero existe otro método más comercial
    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente modificar(@RequestBody Cliente cliente,
                             @PathVariable Long id){

        return clienteService.modificar(cliente,id);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        clienteService.eliminar(id);
    }
}
