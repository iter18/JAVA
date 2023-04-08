package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.AutorLibroDto;
import com.iter.springboot.apirest.dtos.LibroDto;
import com.iter.springboot.apirest.mappers.AutorLibroMapper;
import com.iter.springboot.apirest.mappers.AutorMapper;
import com.iter.springboot.apirest.mappers.LibroMapper;
import com.iter.springboot.apirest.modelo.Autor;
import com.iter.springboot.apirest.modelo.AutorLibro;
import com.iter.springboot.apirest.modelo.Libro;
import com.iter.springboot.apirest.repository.LibroRepository;
import com.iter.springboot.apirest.service.AutorLibroService;
import com.iter.springboot.apirest.service.AutorService;
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
    private LibroRepository libroRepository;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private LibroMapper libroMapper;

    @Autowired
    private AutorMapper autorMapper;

    @Autowired
    private AutorLibroService autorLibroService;

    @Autowired
    private AutorLibroMapper autorLibroMapper;

    @Override
    @Transactional
    public AutorLibroDto alta(LibroDto libroDto, MultipartFile imagen) {
        Assert.hasText(libroDto.getIsbn(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getTitulo(),"Campo TITULO es requerido");
        Assert.hasText(libroDto.getCategoria(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getAutor(),"Campo Autor es requerido");


        Autor autor = autorMapper.toEntity(autorService.buscar(Long.parseLong(libroDto.getAutor())));

        String rutaImagen = uploadFileService.copy(imagen);

        if(rutaImagen == null){
            throw new IllegalArgumentException("La imagen no se subio al directorio");
        }


        Libro libro = libroMapper.toEntity(libroDto);
        libro.setRutaFoto(rutaImagen);
        libro.setFechaRegistro(new Date());

        AutorLibro autorLibro = AutorLibro.builder()
                .autor(autor)
                .libro(libro)
                .build();
       AutorLibroDto autorLibroDto = autorLibroMapper.toDto(autorLibroService.alta(autorLibro));

        autorLibroDto.setLibro(libroMapper.toDto(libroRepository.save(libro)));
        return autorLibroDto;
    }

    @Override
    public List<AutorLibroDto> buscar() {
        List<AutorLibroDto> autorLibroDto = autorLibroMapper.toListDto(autorLibroService.buscar());

        return autorLibroDto;
    }
}
