package com.bolsadeideas.springboot.form.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig  implements WebMvcConfigurer{
	
	@Autowired
	@Qualifier("tiempoTranscurridoInterceptor")
	private HandlerInterceptor TiempoTranscurridoInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		//en la parte final addPathPatterns se usa para especificar una ruta especifica y le estasmo diciendo que sólo sea de form en adelante con los astericos,
		//es decir todo lo que tenga como ruta form
		registry.addInterceptor(TiempoTranscurridoInterceptor).addPathPatterns("/form/**");
	}
	
	

}
