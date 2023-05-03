package com.iter.springboot.apirest.controller;

import com.iter.springboot.apirest.dtos.AltaProductoInventarioDto;
import com.iter.springboot.apirest.dtos.InventarioDto;
import com.iter.springboot.apirest.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/inventarios")
@RestController
public class InventarioController {

    @Autowired
    InventarioService inventarioService;

    @PostMapping("/altaProducto")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<InventarioDto> altaProducto(@RequestBody AltaProductoInventarioDto altaProductoInventarioDto){
        InventarioDto inventarioDto = inventarioService.altaProducto(altaProductoInventarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioDto);
    }

}
