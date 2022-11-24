package com.example.springboot.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {
    //Es para agregar recursos estaticos, como directorios etc.
   /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        //Configuración para mapear y obtener las imágenes del directorio
        //Configuramos la ruta absoluta de manera dinámica
        String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
        log.info("resourcePath: "+ resourcePath);
        //Le especificamos la url de donde estara y los astericos es para indicar la imágen
        registry.addResourceHandler("/uploads/**")
                //Especificamos para obtener la ubicación, el directorio físicamente en este caso externa
                //.addResourceLocations("file:/C:/Temp/uploads/");
                .addResourceLocations(resourcePath);

    }*/
}
