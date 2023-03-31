package com.iter.springboot.apirest.controller;


import com.iter.springboot.apirest.dtos.LibroDto;
import com.iter.springboot.apirest.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class LibroController {

    @Autowired
    UploadFileService uploadFileService;


    @PostMapping(value = "/libros")
    public ResponseEntity<?> alta(@ModelAttribute LibroDto libro,
                                  @RequestParam("imagen")MultipartFile imagen){
        //return ResponseEntity.status(HttpStatus.CREATED).body(autorService.alta(autorDto));
        uploadFileService.copy(imagen);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
