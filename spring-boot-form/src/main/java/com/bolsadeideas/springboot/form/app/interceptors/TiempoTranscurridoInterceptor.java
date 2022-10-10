package com.bolsadeideas.springboot.form.app.interceptors;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("tiempoTranscurridoInterceptor")
public class TiempoTranscurridoInterceptor implements HandlerInterceptor {
	
	/**
	 * @author JAOM
	 * 
	 *Un interceptor es un método que se encargara de realizar cirtas tareas ante un peticíón request
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("TiempoTranscurridoInterceptor: preHandle() entrando...");
		
		if(request.getMethod().equalsIgnoreCase("post")) {
			return true;
		}
		
		
		long tiempoIncio = System.currentTimeMillis();
		request.setAttribute("tiempoIncio", tiempoIncio);
		
		Random random = new Random();
		Integer demora = random.nextInt(500);
		Thread.sleep(demora);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(!request.getMethod().equalsIgnoreCase("post")) {
			long tiempoFin = System.currentTimeMillis();
			long tiempoIncio = (Long) request.getAttribute("tiempoIncio");
			long tiempoTranscurrido = tiempoFin - tiempoIncio;
			
			if(modelAndView != null) {
				modelAndView.addObject("tiempoTranscurrido", tiempoTranscurrido);
			}
			
			log.info("Tiempo Transcurrido: "+tiempoTranscurrido+" millisegundos.");
			log.info("TiempoTranscurridoInterceptor: postHandle() saliendo...");
		}
				
	}

}
