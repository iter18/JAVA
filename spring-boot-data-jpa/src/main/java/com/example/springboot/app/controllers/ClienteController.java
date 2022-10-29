package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.repositorys.ClienteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @Autowired
    private ClienteDao clienteDao;

    @GetMapping(value="/listar")
    public String listar(Model model){
        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",clienteDao.findAll());

        return "listar";
    }
}
