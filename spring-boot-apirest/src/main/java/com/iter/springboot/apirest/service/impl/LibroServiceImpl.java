package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.AutorLibroDto;
import com.iter.springboot.apirest.dtos.ComboDto;
import com.iter.springboot.apirest.dtos.HistoricoProductoDto;
import com.iter.springboot.apirest.dtos.LibroDto;
import com.iter.springboot.apirest.genericos.negocio.impl.AbstractQueryAvanzadoService;
import com.iter.springboot.apirest.mappers.AutorLibroMapper;
import com.iter.springboot.apirest.mappers.AutorMapper;
import com.iter.springboot.apirest.mappers.HistoricoProductoMapper;
import com.iter.springboot.apirest.mappers.LibroMapper;
import com.iter.springboot.apirest.modelo.*;
import com.iter.springboot.apirest.repository.LibroRepository;
import com.iter.springboot.apirest.repository.specification.AutorLibroSpecification;
import com.iter.springboot.apirest.repository.specification.LibroSpecification;
import com.iter.springboot.apirest.service.*;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class LibroServiceImpl extends AbstractQueryAvanzadoService<Libro,Long> implements LibroService {

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

    @Autowired
    private HistoricoProductoMapper historicoProductoMapper;

    @Autowired
    private HistoricoLibroService historicoLibroService;

    @Override
    public JpaSpecificationExecutor<Libro> getJpaSpecificationExecutor() {
        return libroRepository;
    }

    @Override
    public JpaRepository<Libro, Long> getJpaRepository() {
        return libroRepository;
    }

    @Override
    public Sort getOrdenamiento() {
        return Sort.by(Libro_.titulo.getName()).ascending();
    }

    @Override
    @Transactional
    public AutorLibroDto alta(LibroDto libroDto, MultipartFile imagen) {
        Assert.hasText(libroDto.getIsbn(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getTitulo(),"Campo TITULO es requerido");
        Assert.hasText(libroDto.getCategoria(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getAutor(),"Campo Autor es requerido");

        Specification<Libro> filtro = LibroSpecification.isbn(libroDto.getIsbn());
        Optional<Libro> existeIsbn = libroRepository.findOne(filtro);

        if(existeIsbn.isPresent()){
            throw new IllegalArgumentException("Este Isbn se encuentra registrado");
        }

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

    @Override
    public List<ComboDto> buscarC() {
        List<Tuple> registros = libroRepository.buscarC();

        return registros.stream()
                .map(reg -> new ComboDto(Integer.parseInt(reg.get("code").toString()),reg.get("valor").toString()))
                .collect(Collectors.toList());
    }

    @Override
    public AutorLibroDto buscar(Long id) {
        return autorLibroMapper.toDto(autorLibroService.buscarByIdLibro(id));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {

        AutorLibro autorLibro = autorLibroService.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el registro a eliminar"));

        boolean eliminoFoto = uploadFileService.delete(autorLibro.getLibro().getRutaFoto());
        if (!eliminoFoto){
            throw new IllegalArgumentException("La imagen del libro no fue eliminada");
        }
           autorLibroService.eliminar(autorLibro);
           libroRepository.delete(autorLibro.getLibro());

    }

    @Override
    public List<HistoricoProductoDto> consulta(Long id) {

        List<HistoricoLibro> historicoLibroList = historicoLibroService.buscar(id);

        return historicoProductoMapper.toListDto(historicoLibroList);
    }


}
