package com.curso.springboot.horariointerceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("horario")
    private HandlerInterceptor horario;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //excludePathPatterns sirve para excluir rutas alternas de errores o simplemtne ruta que no queremos que aplique el interceptor, en este caso
        //el de cerrado y si quisieramos agregar más de una ruta basta con seperar con comas
        registry.addInterceptor(horario).excludePathPatterns("/cerrado");
    }
}
