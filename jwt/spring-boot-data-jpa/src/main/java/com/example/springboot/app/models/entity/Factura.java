package com.example.springboot.app.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="facturas")
public class Factura implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String descripcion;
    private String observacion;
    //private Double total;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }


    @JoinColumn(name = "factura_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemFactura> itemsFactura;

    public Factura() {
        this.itemsFactura = new ArrayList<ItemFactura>();
    }

    public void addItemsFactura(ItemFactura itemFactura){
        this.itemsFactura.add(itemFactura);
    }

    public Double getTotal(){
        Double total = 0.0;
        int size = itemsFactura.size();
        for (int i=0; i<size; i++){
            total+=itemsFactura.get(i).calcularImporte();
        }
        return total;
    }

}
