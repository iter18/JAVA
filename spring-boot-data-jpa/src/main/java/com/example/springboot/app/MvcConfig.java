package com.example.springboot.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.file.Paths;
import java.util.Locale;

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
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/error_403").setViewName("error_403");
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new  Locale("es","ES"));
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(localeChangeInterceptor());
    }
}
