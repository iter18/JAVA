package com.bolsadeideas.springboot.web.app.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bolsadeideas.springboot.web.app.models.Usuario;

//para definir una ruta primaria o de primer nivel antes de acceder a nuestros metodos se usa RequestMapping
//se importa y se coloca abajo de la anotación controller y se coloca la ruta como se va a llamar 
@Controller
@RequestMapping("/app")
public class IndexController {
	
	//inyectar valores a la aplicacion desde application.properties, en el se puede definir textos, o variables de ambiente como path de urls
	//dentro de properties se coloca llaves o nombre para asignar el valor y es indistinto
	@Value("${value.indexController.index.titulo}")
	private String tituloIndex;
	@Value("${value.indexController.perfil.titulo}")
	private String tituloPerfil;
	@Value("${value.indexController.listar.titulo}")
	private String tituloListar;
	
	/**
	 *  Forma1 de hacerlo 
	 *  Nota: tanto el RequestMapping como el RequestMethod se tienen que importar
	 *  Si no se especifica el metodo por defecto es GET
	 * a = arroba, lo cambie para evitar conflicto
	 * Metodos existentes:
	 * GET = para consulta, ir a una URL
	 * POST = Insertar, pasar parametros interactuar con ellos
	 * PUT = editar
	 * DELETE = eliminar
	aRequestMapping(value = "/index",method=RequestMethod.GET)
	public String index() {
		return "index";
	}*/
	
	/**
	 *  Forma2 de hacerlo 
	 *  Directamente se puede especificar el metodo:
	 *  aGETMapping. aPOSTMapping ,etc. tambien se tienen que importar
	 *  Un metodo puede estar mapeado a mas de una url o petición
	 *  
	 *  Para pasar datos de un cotroller a una vista es de la siguiente manera:
	 *  Manera 1:
	 * */
	@GetMapping({"/index","","/","/home"})
	public String index(Model model) {
		//Model es una implemetacion (Es la más usada en el mercado) que se inyecta aquí para fungir un map de java y así pasar valores, pueden ser objetos 
		// coleccion, map y se basa en la estructura llave = valor
		model.addAttribute("titulo", tituloIndex);
		return "index";
	}
	
	/**
	 * Forma 3 de hacerlo sin la implementación de Spring, directamente utilizando herramienta MAP de JAVA
	 * Nota: la Forma 2 eslo mismo solo cambia la implementación 
	 *
	@GetMapping({"/index","/","/home"})
	public String index(Map<String,Object> map) {
		map.put("titulo", "Hola desde backend / Controller Spring Framework!");
		return "index";
	}*/
	/**
	 * Forma 4 de hacerlo otra implementación de Spring y haciendolo con ModelAndView
	 *
	@GetMapping({"/index","/","/home"})
	public ModelAndView index(ModelAndView mv) {
		mv.addObject("titulo", "Hola desde backend / Controller Spring Framework!");
		mv.setViewName("index");
		return mv;
	}*/
	
	@GetMapping("/perfil")
	public String perfil(Model model) {
		Usuario usuario = new Usuario();
		
		usuario.setNombre("José Aarón");
		usuario.setApellido("Ojeda");
		usuario.setEmail("ti0911342o@gmail.com");
		model.addAttribute("usuario", usuario);
		model.addAttribute("tituloP", tituloPerfil.concat(usuario.getNombre()));
		return "perfil";
	}
	
	@GetMapping("/listar")
	public String listar(Model model) {
		//forma 1 de hacerlo por ArrayList
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(new Usuario("José Antonio" ,"Ojeda", "ti0911342o@gmail.com"));
		usuarios.add(new Usuario("José Aarón" ,"Ojeda", "iter_18@hotmail.com"));
		usuarios.add(new Usuario("Viviana" ,"Bonifacio", "ojedamtzjoseantonio@gmail.com"));
		usuarios.add(new Usuario("Luis" ,"ojeda", "luis@gmail.com"));
		
		
		//forma 2 de hacerlo conviertiendo un array a List
		/*
		List<Usuario> usuarios = Arrays.asList(new Usuario("José Antonio" ,"Ojeda", "ti0911342o@gmail.com"),
				new Usuario("José Aarón" ,"Ojeda", "iter_18@hotmail.com"),
				new Usuario("Viviana" ,"Bonifacio", "ojedamtzjoseantonio@gmail.com"),
				new Usuario("Luis" ,"ojeda", "luis@gmail.com"));*/
		
		model.addAttribute("titulo", tituloListar);
		model.addAttribute("usuarios", usuarios);
		return "listar";
	}
	
	//anotacion aModelAttribute sirve basicamante para mapear un metodo y este este disponible para este controller
	// y las vistas que se encuentran aquí definidas
	
	@ModelAttribute("usuarios")
	public List<Usuario> asignarUsuario(){
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(new Usuario("José Antonio" ,"Ojeda", "ti0911342o@gmail.com"));
		usuarios.add(new Usuario("José Aarón" ,"Ojeda", "iter_18@hotmail.com"));
		usuarios.add(new Usuario("Viviana" ,"Bonifacio", "ojedamtzjoseantonio@gmail.com"));
		usuarios.add(new Usuario("Luis" ,"ojeda", "luis@gmail.com"));
		
		return usuarios;
	}
}
