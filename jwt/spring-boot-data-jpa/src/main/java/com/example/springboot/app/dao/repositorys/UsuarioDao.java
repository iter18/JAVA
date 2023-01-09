package com.example.springboot.app.dao.repositorys;

import com.example.springboot.app.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario,Long>, JpaSpecificationExecutor<Usuario> {

}
