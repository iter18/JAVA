package com.example.springboot.app.dao.repositorys;

import com.example.springboot.app.models.entity.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClienteOptimoDao extends PagingAndSortingRepository<Client,Long> {
}
