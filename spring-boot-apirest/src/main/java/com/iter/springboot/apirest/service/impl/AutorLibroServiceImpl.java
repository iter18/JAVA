package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.modelo.AutorLibro;
import com.iter.springboot.apirest.repository.AutorLibroRepository;
import com.iter.springboot.apirest.repository.specification.AutorLibroSpecification;
import com.iter.springboot.apirest.service.AutorLibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
    public List<AutorLibro> buscar(String isbn, String tiulo, Long autorId) {
        Specification<AutorLibro> filtro = AutorLibroSpecification.likeIsbn(isbn)
                .and(AutorLibroSpecification.likeTitulo(tiulo))
                .and(AutorLibroSpecification.autorId(autorId));

        return  autorLibroRepository.findAll(filtro,Sort.by("libro.titulo").ascending());
    }

    @Override
    public AutorLibro buscarByIdLibro(Long id) {
        Specification<AutorLibro> filtro = AutorLibroSpecification.libroId(id);
        return autorLibroRepository.findOne(filtro)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró ningun registro del libro"));
    }

    @Override
    @Transactional
    public AutorLibro modificar(AutorLibro autorLibro) {
        return autorLibroRepository.saveAndFlush(autorLibro);
    }

    @Override
    public Optional<AutorLibro> buscarPorId(Long id) {
        return autorLibroRepository.findById(id);
    }

    @Override
    public void eliminar(AutorLibro autorLibro) {

        try {
            Long id = autorLibro.getId();
            autorLibroRepository.delete(autorLibro);

        }catch (DataAccessException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }
}
