package com.iter.springboot.apirest.dtos;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutorLibroDto implements Serializable {
    private AutorDto autor;

    private LibroDto libro;
}
