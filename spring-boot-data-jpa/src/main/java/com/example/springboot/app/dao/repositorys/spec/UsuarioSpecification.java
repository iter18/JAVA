package com.example.springboot.app.dao.repositorys.spec;

import com.example.springboot.app.models.entity.Usuario;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {

    public static Specification<Usuario> username(String username){
        return (root, query, builder) -> {
            if(StringUtils.isEmpty(username))
                return builder.conjunction();
            return builder.equal(root.get("username"), username);
        };
    }
}
