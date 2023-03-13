package com.iter.springboot.apirest.excepciones;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ExceptionHelper {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handlerIllegalArgumentException(IllegalArgumentException ex){
        log.error("error{}",ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
