package com.iter.springboot.apirest.repository.specification;

import com.iter.springboot.apirest.modelo.Kardex;
import com.iter.springboot.apirest.modelo.Kardex_;
import com.iter.springboot.apirest.modelo.Libro_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class KardexSpecification {

    public static Specification<Kardex> idLibro(Long id){
        return (root, query, builder) ->{
            if(StringUtils.isEmpty(id.toString()))
                return builder.conjunction();
            return builder.equal(root.get(Kardex_.libro.getName()).get(Libro_.id.getName()),id);
        };
    }
}
