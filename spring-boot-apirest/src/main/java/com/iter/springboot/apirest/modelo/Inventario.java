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
@Table(name = "INVENTARIOS")
public class Inventario  implements Serializable {

    private static final long serialVersionUID = -443510922152256525L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stock;
    private Integer minimo;
    private Double precioCompra;
    private Double precioVenta;
    private Integer baja;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LIBRO", referencedColumnName = "ID")
    private Libro libro;
}
