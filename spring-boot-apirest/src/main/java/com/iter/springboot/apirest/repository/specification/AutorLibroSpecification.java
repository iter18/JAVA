package com.iter.springboot.apirest.repository.specification;

import com.iter.springboot.apirest.modelo.AutorLibro;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class AutorLibroSpecification {

    public static Specification<AutorLibro> likeIsbn(String isbn){
        return (root, query, builder) ->{
            if(StringUtils.isEmpty(isbn))
                return builder.conjunction();
            return builder.like(builder.upper(root.get("libro").get("isbn")),"%"+isbn.toUpperCase()+"%");
        };
    }

    public static Specification<AutorLibro> likeTitulo(String titulo){
        return (root, query, builder)->{
            if(StringUtils.isEmpty(titulo))
                return builder.conjunction();
            return builder.like(builder.upper(root.get("libro").get("titulo")),"%"+titulo.toUpperCase()+"%");
        };
    }
    public static Specification<AutorLibro> autorId(Long autorId){
        return (root, query, builder) -> {
          if(autorId == null)
              return builder.conjunction();
            return builder.equal(root.get("autor").get("id"),autorId);
        };
    }
}
