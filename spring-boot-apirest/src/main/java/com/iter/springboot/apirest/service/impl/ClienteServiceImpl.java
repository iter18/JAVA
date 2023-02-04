package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.modelo.Cliente;
import com.iter.springboot.apirest.repository.ClienteRepository;
import com.iter.springboot.apirest.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public List<Cliente> buscar() {

        return clienteRepository.findAll();
    }
}