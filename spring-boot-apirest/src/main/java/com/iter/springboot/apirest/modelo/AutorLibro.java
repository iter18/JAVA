package com.iter.springboot.apirest.modelo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "AUTORES_LIBROS")
public class AutorLibro implements Serializable {
    private static final long serialVersionUID = 4336587486637366501L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "ID_AUTOR", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    Autor autor;

    @JoinColumn(name = "ID_LIBRO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    Libro libro;

}
