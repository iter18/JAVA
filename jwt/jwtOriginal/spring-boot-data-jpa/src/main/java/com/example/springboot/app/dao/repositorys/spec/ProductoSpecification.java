package com.example.springboot.app.dao.repositorys.spec;

import com.example.springboot.app.models.entity.Producto;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;

public class ProductoSpecification {

    public static Specification<Producto> nombreLike(String nombre){
        return (root, query, builder) -> {
            if(StringUtils.isEmpty(nombre))
                return builder.conjunction();
            return builder.like(root.get("nombre"), "%"+nombre+"%");
        };
    }
}
