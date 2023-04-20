package com.iter.springboot.apirest.dtos;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutorLibroDto implements Serializable {

    private static final long serialVersionUID = 6163152443443958677L;
    private Long id;
    private AutorDto autor;

    private LibroDto libro;
}
