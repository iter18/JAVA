package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.services.ClienteOptimoService;
import com.example.springboot.app.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    ClienteOptimoService clienteOptimoService;

    @GetMapping(value="/listar")
    public  List<Client> listar() {

        return clienteOptimoService.findAll();
    }
}
