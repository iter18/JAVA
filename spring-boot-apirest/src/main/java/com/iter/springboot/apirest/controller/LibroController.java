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
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LibroController {

    @Autowired
    LibroService libroService;

    @GetMapping("/libros")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<AutorLibroDto>> buscar(@RequestParam("isbnLibro")Optional<String> isbn,
                                                      @RequestParam("tituloLibro") Optional<String> titulo,
                                                      @RequestParam("autorLibro") Optional<String> autorCode){
        Long autorId = autorCode.isEmpty() ? null : Long.parseLong(autorCode.get());
        return ResponseEntity.status(HttpStatus.OK).body(libroService.buscar(isbn.orElse(""), titulo.orElse(""),autorId ));
    }

    @PostMapping("/libros")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<AutorLibroDto> alta(@ModelAttribute LibroDto libro,
                                  @RequestParam("imagen")MultipartFile imagen){
        return  ResponseEntity.status(HttpStatus.CREATED).body(libroService.alta(libro,imagen));

    }

    @PutMapping("/libros/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<AutorLibroDto> modificar(@PathVariable Long id,
                                                    @ModelAttribute LibroDto libro,
                                                    @RequestParam("imagen")MultipartFile imagen){
        AutorLibroDto  autorLibroDto = libroService.modificar(id,libro,imagen);
        return  ResponseEntity.status(HttpStatus.CREATED).body(autorLibroDto);

    }
}
