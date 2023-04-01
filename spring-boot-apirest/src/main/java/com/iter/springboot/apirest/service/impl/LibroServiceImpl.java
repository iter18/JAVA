package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.LibroDto;
import com.iter.springboot.apirest.mappers.LibroMapper;
import com.iter.springboot.apirest.modelo.Libro;
import com.iter.springboot.apirest.repository.LibroRepository;
import com.iter.springboot.apirest.service.LibroService;
import com.iter.springboot.apirest.service.UploadFileService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class LibroServiceImpl implements LibroService {

    @Autowired
    LibroRepository libroRepository;

    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    LibroMapper libroMapper;

    @Override
    @Transactional
    public LibroDto alta(LibroDto libroDto, MultipartFile imagen) {
        Assert.hasText(libroDto.getIsbn(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getTitulo(),"Campo TITULO es requerido");
        Assert.hasText(libroDto.getCategoria(),"Campo ISBN es requerido");

        String rutaImagen = uploadFileService.copy(imagen);

        if(rutaImagen == null){
            throw new IllegalArgumentException("La imagen no se subio al directorio");
        }

        Libro libro = libroMapper.toEntity(libroDto);
        libro.setRutaFoto(rutaImagen);
        libro.setFechaRegistro(new Date());

        return libroMapper.toDto(libroRepository.save(libro));
    }

    @Override
    public List<LibroDto> buscar() {
        return libroMapper.toListDto(libroRepository.findAll());
    }
}
