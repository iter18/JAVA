package com.example.springboot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //Es para agregar recursos estaticos, como directorios etc.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        //Configuración para mapear y obtener las imágenes del directorio
        //Le especificamos la url de donde estara y los astericos es para indicar la imágen
        registry.addResourceHandler("/uploads/**")
                //Especificamos para obtener la ubicación, el directorio físico
                .addResourceLocations("file:/C:/Temp/uploads/");
    }
}
