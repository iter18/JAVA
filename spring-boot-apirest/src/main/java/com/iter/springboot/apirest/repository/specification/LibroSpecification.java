package com.iter.springboot.apirest.repository.specification;

import com.iter.springboot.apirest.modelo.Libro;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class LibroSpecification {

    public static Specification<Libro> idNotEqual(Long id){
        return (root, query, builder) ->{
            if(StringUtils.isEmpty(id.toString()))
                return builder.conjunction();
            return builder.notEqual(root.get("id"),id);
        };
    }

    public static Specification<Libro> isbn(String isbn){
        return (root, query, builder) ->{
            if(StringUtils.isEmpty(isbn))
                return builder.conjunction();
            return builder.equal(builder.upper(root.get("isbn")),isbn.toUpperCase());
        };
    }
}
