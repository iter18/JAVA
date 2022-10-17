package com.curso.springbooterror.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;
import java.util.Date;

/**
 * @author José Antonio
 *
 * Controller especializado para el manejo de excepciones, para eso se usa  @ControllerAdvice
 */

@ControllerAdvice
public class ErrorHandlerController {

    //La anotación funciona para maper las excepciones, para decalrar mas de un tipo de excepcion se usan las llaves
    @ExceptionHandler({ArithmeticException.class, NumberFormatException.class, NullPointerException.class})
    public String arithmeticError(Exception ex, Model model){
        //model.addAttribute("error","Error de tipo aritmético");
        model.addAttribute("error","Error de origen");
        model.addAttribute("message",ex.getMessage());
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("timestamp", new Date());
        //return "error/aritmetic";
        return  "error/excepciones";
    }
}
