package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.modelo.Cliente;
import com.iter.springboot.apirest.repository.ClienteRepository;
import com.iter.springboot.apirest.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Override
    public Cliente buscar(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cliente alta(Cliente cliente) {
        Cliente clienteA = Cliente.builder()
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .apellido(cliente.getApellido())
                .createAt(new Date())
                .build();
        return clienteRepository.save(clienteA);
    }

    @Override
    @Transactional
    public Cliente modificar(Cliente cliente, Long id) {

        return clienteRepository.saveAndFlush(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
