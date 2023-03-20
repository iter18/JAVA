package com.iter.springboot.apirest.controller;

import com.iter.springboot.apirest.dtos.AutorDto;
import com.iter.springboot.apirest.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AutorController {

    @Autowired
    AutorService autorService;

    @PostMapping("/autores")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<AutorDto>alta(@RequestBody AutorDto autorDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.alta(autorDto));
    }

    @GetMapping("/autores")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<AutorDto>> buscar(){
        return ResponseEntity.ok(autorService.buscar());
    }

}