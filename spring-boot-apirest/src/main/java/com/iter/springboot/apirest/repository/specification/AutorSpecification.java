package com.iter.springboot.apirest.repository.specification;

import com.iter.springboot.apirest.modelo.Autor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class AutorSpecification {

    public static Specification<Autor> nombre(String nombre){
        return (root, query, builder) ->{
            if(StringUtils.isEmpty(nombre))
                return builder.conjunction();
            return builder.equal(builder.upper(root.get("nombre")),nombre.toUpperCase());
        };
    }

    public static Specification<Autor> apellido(String apellido){
        return (root, query, builder) -> {
            if (StringUtils.isEmpty(apellido))
                return builder.conjunction();
            return builder.equal(builder.upper(root.get("apellido")),apellido.toUpperCase());
        };
    }

    public static Specification<Autor>likeNombre(String nombre){
        return (root, query, builder) ->{
            if (StringUtils.isEmpty(nombre))
                return builder.conjunction();
            return builder.like(builder.upper(root.get("nombre")),"%"+nombre.toUpperCase()+"%" );
        };
    }

}
