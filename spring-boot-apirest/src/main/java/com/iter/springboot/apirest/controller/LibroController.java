package com.iter.springboot.apirest.controller;


import com.iter.springboot.apirest.dtos.AutorLibroDto;
import com.iter.springboot.apirest.dtos.ComboDto;
import com.iter.springboot.apirest.dtos.HistoricoProductoDto;
import com.iter.springboot.apirest.dtos.LibroDto;
import com.iter.springboot.apirest.service.LibroService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
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
    @GetMapping("/libros/combo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<ComboDto>> buscarC(){
        return ResponseEntity.status(HttpStatus.OK).body(libroService.buscarC());
    }

    @GetMapping("/libros/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<AutorLibroDto> buscarById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(libroService.buscar(id));
    }

    @GetMapping("/libros/historico/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<HistoricoProductoDto>> buscar(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(libroService.consulta(id));
    }

    @GetMapping("/libros/historicoReporte/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Resource> generarInformeLibro(@PathVariable Long id) throws Exception {

        return libroService.exportInvoice(id);
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

    @DeleteMapping("libros/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id){
        libroService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
