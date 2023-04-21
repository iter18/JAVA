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
import com.iter.springboot.apirest.repository.specification.LibroSpecification;
import com.iter.springboot.apirest.service.AutorLibroService;
import com.iter.springboot.apirest.service.AutorService;
import com.iter.springboot.apirest.service.LibroService;
import com.iter.springboot.apirest.service.UploadFileService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public List<AutorLibroDto> buscar(String isbn, String titulo, Long autorCode) {

        List<AutorLibro> lista = autorLibroService.buscar(isbn,titulo,autorCode);
        log.info(lista.toString());
        List<AutorLibroDto> autorLibroDto = autorLibroMapper.toListDto(autorLibroService.buscar(isbn, titulo, autorCode));

        return autorLibroDto;
    }

    @Override
    @Transactional
    public AutorLibroDto modificar(Long id, LibroDto libroDto, MultipartFile imagen) {
        Assert.hasText(libroDto.getIsbn(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getTitulo(),"Campo TITULO es requerido");
        Assert.hasText(libroDto.getCategoria(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getAutor(),"Campo Autor es requerido");

        Autor autor = autorMapper.toEntity(autorService.buscar(Long.parseLong(libroDto.getAutor())));
        AutorLibro autorLibro = autorLibroService.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el registro a modificar"));
        Libro libro = libroRepository.findById(Long.parseLong(libroDto.getIdLibro()))
                .orElseThrow(() -> new EntityNotFoundException("El registro del libro no fue encontrado"));

        //Validación para que no se duplique el ISBN
        Specification<Libro> filtro = LibroSpecification.idNotEqual(libro.getId())
                .and(LibroSpecification.isbn(libroDto.getIsbn()));
        Optional<Libro> isbnDuplicado = libroRepository.findOne(filtro);
        if(isbnDuplicado.isPresent()){
            throw new IllegalArgumentException("El ISBN ya existe");
        }
       /* if(libro.getTitulo().equalsIgnoreCase(libroDto.getTitulo())){
            throw new IllegalArgumentException("El Titulo del libro ya existe");
        }*/
        //Eliminamos imagen anterior
        log.info("Nombre imagen"+ imagen.getOriginalFilename());
        if(!imagen.getOriginalFilename().equals("") ||
                !imagen.getOriginalFilename().isEmpty()||
                !imagen.getOriginalFilename().isBlank()){
            boolean eliminoFoto = uploadFileService.delete(libro.getRutaFoto());
            if (!eliminoFoto){
                throw new IllegalArgumentException("La imagen anterior no fue eliminada");
            }
            String rutaImagen = uploadFileService.copy(imagen);
            if(rutaImagen == null){
                throw new IllegalArgumentException("La imagen no se subio al directorio");
            }
            libro.setRutaFoto(rutaImagen);
        }

        libro.setIsbn(libroDto.getIsbn());
        libro.setTitulo(libroDto.getTitulo());
        libro.setEditorial(libroDto.getEditorial());
        libro.setCategoria(libroDto.getCategoria());
        libro.setFechaRegistro(new Date());

        autorLibro.setAutor(autor);
        autorLibro.setLibro(libro);
        return  autorLibroMapper.toDto(autorLibroService.modificar(autorLibro));

    }
}
