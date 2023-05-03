package com.iter.springboot.apirest.repository.specification;

import com.iter.springboot.apirest.modelo.Inventario;
import com.iter.springboot.apirest.modelo.Inventario_;
import com.iter.springboot.apirest.modelo.Libro_;
import org.springframework.data.jpa.domain.Specification;

public class InventarioSpecification {

    public static Specification<Inventario> idLibro(Long id){
        return (root,query,builder)->{
            if(id == null)
                return builder.conjunction();
            return builder.equal(root.get(Inventario_.libro).get(Libro_.id),id);
        };
    }
}
