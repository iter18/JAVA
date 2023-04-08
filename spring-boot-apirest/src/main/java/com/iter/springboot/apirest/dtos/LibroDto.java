package com.iter.springboot.apirest.dtos;


import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibroDto implements Serializable {

    private static final long serialVersionUID = -5495879811709755725L;
    private Long id;
    private String isbn;
    private String titulo;
    private String categoria;
    private String editorial;
    private String rutaFoto;
    private String autor;
    private Date fechaRegistro;
}
