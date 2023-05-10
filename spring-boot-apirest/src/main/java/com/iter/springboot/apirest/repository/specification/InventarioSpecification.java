package com.iter.springboot.apirest.repository.specification;

import com.iter.springboot.apirest.modelo.Inventario;
import com.iter.springboot.apirest.modelo.Inventario_;
import com.iter.springboot.apirest.modelo.Libro_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class InventarioSpecification {

    public static Specification<Inventario> idLibro(Long id){
        return (root,query,builder)->{
            if(id == null)
                return builder.conjunction();
            return builder.equal(root.get(Inventario_.libro).get(Libro_.id),id);
        };
    }

    public static Specification<Inventario> tituloLibro(String tituloLibro){
        return (root, query, builder) ->{
            if(StringUtils.isEmpty(tituloLibro))
                return builder.conjunction();
            return builder.like(builder.upper(root.get(Inventario_.libro.getName())
                                            .get(Libro_.titulo.getName())), "%"+tituloLibro.toUpperCase()+"%");
        };
    }

    public static Specification<Inventario> isbnLibro(String isbnLibro){
        return (root, query, builder) ->{
            if(StringUtils.isEmpty(isbnLibro))
                return builder.conjunction();
            return builder.like(builder.upper(root.get(Inventario_.libro.getName())
                    .get(Libro_.isbn.getName())),"%"+isbnLibro.toUpperCase()+"%" );
        };
    }
}
