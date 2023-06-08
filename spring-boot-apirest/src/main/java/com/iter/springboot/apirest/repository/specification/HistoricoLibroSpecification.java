package com.iter.springboot.apirest.repository.specification;

import com.iter.springboot.apirest.modelo.HistoricoLibro;
import com.iter.springboot.apirest.modelo.HistoricoLibro_;
import com.iter.springboot.apirest.modelo.Libro_;
import org.springframework.data.jpa.domain.Specification;

public class HistoricoLibroSpecification {

    public static Specification<HistoricoLibro> idLibro(Long id){
        return (root, query, builder) ->{
            if(id == null)
                return builder.conjunction();
            return builder.equal(root.get(HistoricoLibro_.libro).get(Libro_.id),id);
        };
    }
}
