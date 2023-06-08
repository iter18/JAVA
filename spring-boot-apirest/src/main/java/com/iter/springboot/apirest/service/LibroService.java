package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.dtos.AutorLibroDto;
import com.iter.springboot.apirest.dtos.ComboDto;
import com.iter.springboot.apirest.dtos.HistoricoProductoDto;
import com.iter.springboot.apirest.dtos.LibroDto;
import com.iter.springboot.apirest.genericos.negocio.QueryAvanzadoService;
import com.iter.springboot.apirest.modelo.Libro;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LibroService extends QueryAvanzadoService<Libro,Long> {

    List<AutorLibroDto> buscar(String isbn, String titulo, Long autorCode);
    List<ComboDto> buscarC();
    AutorLibroDto buscar(Long id);
    AutorLibroDto alta(LibroDto libroDto, MultipartFile imagen);
    AutorLibroDto modificar(Long id, LibroDto libroDto, MultipartFile imagen);
    void eliminar(Long id);

    List<HistoricoProductoDto> consulta(Long id);

}
