package com.iter.springboot.apirest.controller;


import com.iter.springboot.apirest.dtos.AutorLibroDto;
import com.iter.springboot.apirest.dtos.LibroDto;
import com.iter.springboot.apirest.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibroController {

    @Autowired
    LibroService libroService;

    @GetMapping("/libros")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<AutorLibroDto>> buscar(){
        return ResponseEntity.status(HttpStatus.OK).body(libroService.buscar());
    }

    @PostMapping("/libros")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<AutorLibroDto> alta(@ModelAttribute LibroDto libro,
                                  @RequestParam("imagen")MultipartFile imagen){
        return  ResponseEntity.status(HttpStatus.CREATED).body(libroService.alta(libro,imagen));

    }
}
