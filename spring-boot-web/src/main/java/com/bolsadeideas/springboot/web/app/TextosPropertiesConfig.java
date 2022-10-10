package com.bolsadeideas.springboot.web.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 
 * @author Home B
 * Se creaa clase de configuración para archivos Properties con la @Configuration = para indicar que será una clase de configuración
 * luego se usa otra anotación @PropertySources para establecer en un array con la anotación @PropertySource cada archivo de propiedad especificando la ruta
 * donde se ubica, en este caso pues la raíz por eso se puso classpath y el nombre del archivo.
 * Tambien se pueden especificar más de un archivo de propiedad separado por comas
 */
@Configuration
@PropertySources({
	@PropertySource("classpath:texto.properties")
})
public class TextosPropertiesConfig {

}
