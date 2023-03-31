package com.iter.springboot.apirest.dtos;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibroDto implements Serializable {

    private String isbn;
    private String titulo;
    private String categoria;
    private String editorial;
    private String rutaFoto;
    private Date fechaRegistro;
}
