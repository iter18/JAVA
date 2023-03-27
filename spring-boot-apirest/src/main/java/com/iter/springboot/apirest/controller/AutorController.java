package com.iter.springboot.apirest.controller;

import com.iter.springboot.apirest.dtos.AutorDto;
import com.iter.springboot.apirest.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<AutorDto>> buscar(@RequestParam("nombreAutor") Optional<String> nombre){
        return ResponseEntity.ok(autorService.buscar(nombre.orElse("")));
    }

    @GetMapping("/autores/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<AutorDto> buscar(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(autorService.buscar(id));
    }

    @PutMapping("/autores/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<AutorDto> modificar(@PathVariable Long id,@RequestBody AutorDto autorDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.modificar(id,autorDto));
    }

    @DeleteMapping("/autores/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id){
        autorService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
