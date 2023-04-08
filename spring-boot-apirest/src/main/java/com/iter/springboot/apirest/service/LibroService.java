package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.dtos.AutorLibroDto;
import com.iter.springboot.apirest.dtos.LibroDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LibroService {


    AutorLibroDto alta(LibroDto libroDto, MultipartFile imagen);

    List<AutorLibroDto> buscar();
}
