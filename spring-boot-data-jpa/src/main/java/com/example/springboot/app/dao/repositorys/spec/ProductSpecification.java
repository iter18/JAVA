package com.example.springboot.app.dao.repositorys.spec;

import com.example.springboot.app.models.entity.Product;
import com.example.springboot.app.models.entity.Producto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> nombreLike(String nombre){
        return (root, query, builder) -> {
            if(StringUtils.isEmpty(nombre))
                return builder.conjunction();
            return builder.like(root.get("nombre"), "%"+nombre+"%");
        };
    }
}
