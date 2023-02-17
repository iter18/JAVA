package com.iter.springboot.apirest.controller;


import com.iter.springboot.apirest.modelo.Cliente;
import com.iter.springboot.apirest.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity<HashMap<String,Object>> buscar(@PathVariable Long id){

        HashMap<String, Object> map = new HashMap<>();
        try{
             Cliente cliente = clienteService.buscar(id);

             if(cliente == null){
                 map.put("mensaje","El cliente que desea buscar no existe!!");
                 return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
             }
             map.put("reg",cliente);
             return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (DataAccessException e){
            map.put("mensaje", "Error al realizar consulta en DB");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e){
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Este método es una forma haciendo directamente con objetos, pero existe otro método más comercial para retornar mensajes y objetos
    @PostMapping("/clientes")
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
