package com.iter.springboot.apirest.modelo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "DEVOLUCION_LIBROS")
public class DevolucionLibro implements Serializable {

    private static final long serialVersionUID = -6493832411714844985L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private String observacion;
    private Date fecha;

    @JoinColumn(name = "ID_LIBRO",referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Libro libro;

    @JoinColumn(name = "ID_USUARIO" , referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;


}
