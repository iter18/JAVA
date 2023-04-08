package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.modelo.AutorLibro;
import com.iter.springboot.apirest.repository.AutorLibroRepository;
import com.iter.springboot.apirest.service.AutorLibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AutorLibroServiceImpl implements AutorLibroService {

    @Autowired
    private AutorLibroRepository autorLibroRepository;

    @Override
    @Transactional
    public AutorLibro alta(AutorLibro autorLibro) {
        return autorLibroRepository.save(autorLibro);
    }

    @Override
    public List<AutorLibro> buscar() {
        return autorLibroRepository.findAll();
    }
}
