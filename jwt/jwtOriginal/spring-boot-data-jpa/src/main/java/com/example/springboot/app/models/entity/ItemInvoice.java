package com.example.springboot.app.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="factura_items")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemInvoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private static final long serialVersionUID = 1L;

    private Integer cantidad;

    private Double importe;


    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "factura_id")
    private Invoice factura;

}
