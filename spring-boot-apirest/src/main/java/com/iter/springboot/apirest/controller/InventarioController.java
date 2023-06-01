package com.iter.springboot.apirest.controller;

import com.iter.springboot.apirest.dtos.ProductoInventarioDto;
import com.iter.springboot.apirest.dtos.InventarioDto;
import com.iter.springboot.apirest.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/inventarios")
@RestController
public class InventarioController {

    @Autowired
    InventarioService inventarioService;

    @PostMapping("/altaProducto")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<InventarioDto> altaProducto(@RequestBody ProductoInventarioDto productoInventarioDto){
        InventarioDto inventarioDto = inventarioService.altaProducto(productoInventarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioDto);
    }

    @GetMapping("/buscarProductos")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Transactional
    public ResponseEntity<List<InventarioDto>> buscarProducto(@RequestParam Optional<String> isbnLibro, @RequestParam Optional<String> tituloLibro){
        return ResponseEntity.status(HttpStatus.OK).body(inventarioService.buscar(isbnLibro.orElse(""), tituloLibro.orElse("")));
    }

    @PutMapping("/modificarProducto")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<InventarioDto> modificar(@RequestBody ProductoInventarioDto productoInventarioDto){
        InventarioDto inventarioDto = inventarioService.modificarProducto(productoInventarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioDto);
    }

}
