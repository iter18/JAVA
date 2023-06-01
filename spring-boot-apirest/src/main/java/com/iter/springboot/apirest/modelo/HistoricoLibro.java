package com.iter.springboot.apirest.modelo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "HISTORICO_LIBROS")
public class HistoricoLibro  implements Serializable {

    private static final long serialVersionUID = 2131885979815015185L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private LocalDateTime fecha;
    private Integer minimo;
    private Double precioCompra;
    private Double precioVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LIBRO", referencedColumnName = "ID")
    private Libro libro;

    @JoinColumn(name = "ID_MOVIMIENTO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

}
