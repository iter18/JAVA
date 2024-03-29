package com.iter.springboot.apirest.excepciones;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ExceptionHelper {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handlerIllegalArgumentException(IllegalArgumentException ex){
        log.error("error{}",ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<Object> handlerDataAccessException(DataAccessException ex){
        log.error("error{}",ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object>handlerEntityNotFoundException(EntityNotFoundException ex){
        log.error("error{}",ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<Object>handlerNumberFormatException(NumberFormatException ex){
        log.error("error{}",ex.getMessage());
        return new ResponseEntity<>("No se puede realizar un cast por que esta nulo",HttpStatus.NO_CONTENT);
    }

    /*@ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handlerException(Exception ex){
        log.error("error{}",ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
}
