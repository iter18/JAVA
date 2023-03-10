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

    /*@GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.buscar();
    }*/

    @GetMapping("/clientes")
    public ResponseEntity<HashMap<String,Object>> buscar(){
        HashMap<String,Object> map = new HashMap<>();
        try {
            List<Cliente> clienteList = clienteService.buscar();
            map.put("data",clienteList);
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
    /*@GetMapping("/clientes/{id}")
    public Cliente buscar(@PathVariable Long id){
        Cliente cliente = clienteService.buscar(id);
        return cliente;
    }*/
    //De esta manera se hace para retornar una estructura más comercial para manejar json
    @GetMapping("/clientes/{id}")
    public ResponseEntity<HashMap<String,Object>> buscar(@PathVariable Long id){

        HashMap<String, Object> map = new HashMap<>();
        try{
             Cliente cliente = clienteService.buscar(id);
             map.put("reg",cliente);
             return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (IllegalArgumentException ex){
            map.put("mensaje",ex.getMessage());
            map.put("error","error.io");
            return new ResponseEntity<>(map,HttpStatus.CONFLICT);

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
    /*@PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente alta(@RequestBody Cliente cliente){
        return clienteService.alta(cliente);
    }*/


    /*
    * Existen varias formas de pedir un objeto a un rest:
    * 1 por tipo de objeto = entity
    * 2 por tipo de objeto = dto
    * 3 HashMap<key,value>  = es para simular un objeto generico y no encasillar hacia un tipo de objeto.
    *  Puede haber el caso en que existan 2 objetos con mismos atributos y se pueda reutilizar el método
    *
    * Dependiendo la necesidad es el que se usara.
    * */
    @PostMapping("/clientes")
    //Ejemplo con objeto entity
            // public ResponseEntity<HashMap<String,Object>> alta(@RequestBody Cliente cliente){
    //Ejmplo usando un HashMap de forma  generica para no esterotipar a un objeto especifico
    public ResponseEntity<HashMap<String,Object>> alta(@RequestBody HashMap<String,?> cliente){
        HashMap<String,Object> map = new HashMap<>();
        try {
            Cliente cliente1 = clienteService.altaHM(cliente);
            map.put("reg",cliente1);
            map.put("mensaje","Cliente creado con éxito!");
            return new ResponseEntity<>(map,HttpStatus.CREATED);

        }catch (DataAccessException e){
            map.put("mensaje", "Error al realizar transacción  en DB");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e){
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //Este método es una forma haciendo directamente con objetos, pero existe otro método más comercial
    @PutMapping("/clientes/{id}")
    public ResponseEntity<HashMap<String,Object>> modificar(@RequestBody Cliente cliente,
                             @PathVariable Long id){
        HashMap<String,Object> map = new HashMap<>();
        try{
            Cliente cli =clienteService.modificar(cliente,id);
            map.put("reg",cli);
            map.put("mensaje","Registro modificado éxitosamente");
            return new ResponseEntity<>(map,HttpStatus.CREATED);

        }catch (IllegalArgumentException ex){
            map.put("mensaje",ex.getMessage());
            map.put("error","error.io");
            return new ResponseEntity<>(map,HttpStatus.CONFLICT);

        }catch(DataAccessException e){
            map.put("mensaje", "Error al realizar transacción  en DB");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e){
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<HashMap<String,Object>> eliminar(@PathVariable Long id){

        HashMap<String,Object> map = new HashMap<>();
        try{
            clienteService.eliminar(id);
            map.put("mensaje","Registro eliminado éxitosamente");
            return new ResponseEntity<>(map,HttpStatus.NO_CONTENT);

        }catch (IllegalArgumentException ex){
            map.put("mensaje",ex.getMessage());
            map.put("error","error.io");
            return new ResponseEntity<>(map,HttpStatus.CONFLICT);

        }catch(DataAccessException e){
            map.put("mensaje", "Error al realizar transacción  en DB");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e){
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
