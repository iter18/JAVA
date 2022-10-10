package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisEditor;
import com.bolsadeideas.springboot.form.app.editors.RolPropertiesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Rol;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.service.PaisService;
import com.bolsadeideas.springboot.form.app.service.RolService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

/*
 * @SessionAttributes indica que va a persistir el objeto entity y todos sus atributos hasta que se cierre el navegador,
 * de esta forma podemos tener atributos del entity sin que se defina en el front, es más para manejar procesos internos
 * con la metodo SessionStatus de spring finalizamos la sesion y se destruye el valor del atributo cuando finalice su proceso a realizar
 */
@Controller
@SessionAttributes("usuario")
public class FormController {
	
	@Autowired
	private UsuarioValidador usuarioValidador;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private PaisEditor paisEditors;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private RolPropertiesEditor rolPropertiesEditor;
	
	/*
	 * InitBinder sirve para incializar procesos, validaciones antes de invocar los metódos del controllador
	 * en este caso se esta inicializando para que valide apartir de la inyeccion de UsuarioValidador,
	 * De esta forma ya no es necesario especificar en los metodos usuarioValidador.validate(usuario, result) por que automaticamente se los pasa los metodos
	 * que se le hayan definido previamente el algun tipo de validacion en el bean o clase de validacion
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(usuarioValidador);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);//es para indicar que no debe ser flexible con false con el valor recibido
		//binder.registerCustomEditor es un filtro para convertir un parametro recibido a un tipo de objeto
		binder.registerCustomEditor(Date.class, "fechaNacimiento",new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, "nombre",new NombreMayusculaEditor());
		binder.registerCustomEditor(String.class, "apellido",new NombreMayusculaEditor());
		binder.registerCustomEditor(Pais.class, "pais", paisEditors);
		binder.registerCustomEditor(Rol.class, "roles", rolPropertiesEditor);
		
		
	}
	
	@ModelAttribute("paises")
	public List<String> paises() {
		return Arrays.asList("México","España","Chile","Perú","Colombia");
	}
	
	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString() {
		List<String> listRoles = new ArrayList<>();
		listRoles.add("ROLE_ADMIN");
		listRoles.add("ROLE_USER");
		listRoles.add("ROLE_MODERATOR");
		return listRoles;
	}
	
	@ModelAttribute("listaRolesMap")
	public Map<String,String> listaRolesMap() {
		Map<String,String> rol = new HashMap<>();
		rol.put("ROLE_ADMIN","Administrador");
		rol.put("ROLE_USER","Usuario");
		rol.put("ROLE_MODERATOR","Moderador");
		return rol;
	}
	
	@ModelAttribute("roles")
	public List<Rol> Roles() {

		return rolService.listar();
	}
	
	
	@ModelAttribute("paisesMap")
	public Map<String,String> paisesMap() {
		Map<String,String> paises = new HashMap<>();
		
		paises.put("MX", "México");
		paises.put("ES", "España");
		paises.put("CL", "Chile");
		paises.put("PE", "Perú");
		paises.put("CO", "Colomia");
		
		return paises;
	}
	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises() {
		
		return paisService.listar();
	}
	
	@ModelAttribute("genero")
	public List<String> genero() {
		
		return Arrays.asList("Hombre","Mujer");
	}
		

	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Jose Antonio");
		usuario.setApellido("Ojeda");
		usuario.setHabilitar(true);
		usuario.setValorSecreto("Algún valor secreto");
		//Pooblar mediante método equals
		usuario.setRoles(Arrays.asList(
				new Rol(2,"ROLE_USER","Usuario")));
		//poblar mediante services
		//usuario.setRoles(rolService.buscarById(Arrays.asList(2)));
		usuario.setIdentificador("123-45saasKEQe");
		//Poblar mediante método toString
		usuario.setPais(new Pais(2,"España","ES"));
		//Poblar mediante uso de service
		//usuario.setPais(paisService.buscarById(3));
		model.addAttribute("titulo", "Formulario");
		model.addAttribute("usuario", usuario);
		return "form";
	}

	// Forma de hacerlo con Lombok tradicional en un objeto
	/*
	 * @PostMapping("/form") public String procesar(Model model,
	 * 
	 * @RequestParam("username")String username,
	 * 
	 * @RequestParam("password")String pass,
	 * 
	 * @RequestParam("email")String correo) {
	 * 
	 * 
	 * Usuario usuario = Usuario.builder() .username(username) .password(pass)
	 * .email(correo) .build();
	 * model.addAttribute("titulo","Valores del formulario");
	 * model.addAttribute("usuario", usuario);
	 * 
	 * return "resultado"; }
	 */
	/*
	 * Forma de hacerlo más simple mapeando los campos del entity con respecto al formulario, 
	 * pero tienen que ser exactamente igual los campos definidos del entity y del formulario u objeto a recibir 
	 */
	
	/*
	 * @Valid-> es una anotación de JAVAX API para validar parametros, es como el Assert de spring, es decir son 
	 * precondiciones que se deben de cumplir antes de ejecutar el proceso.
	 * 
	 * BindingResult es una clase interfaz que implementa los metodos para mostrar los mensajes de erro cuando se usa
	 * la etiqueta  @Valid, siempre debe ir esta instrucción despues del objeto a validar marcado con la etiqueta @Valid
	 */
	 @PostMapping("/form")
	public String procesar(@Valid Usuario usuario,BindingResult result, Model model) {
		 
		 //usuarioValidador.validate(usuario, result);
		 
		 //mostrar errores al front usando MAP de HashMap de la forma manual iterando uno por uno y personalizando errores
		 /*if(result.hasErrors()) {
			Map<String,String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(), "Eror en el campo ".concat(err.getField().concat(" ").concat(err.getDefaultMessage())));
			});
			model.addAttribute("error", errores);
			return "form";
		 }*/
		 //mostrar errores al front haciendolo de manera automatica y un poco más generica
		 if(result.hasErrors()) {
			 model.addAttribute("titulo", "Valores del formulario");
			return "form";
		 }
		 //finaliza o mata el valor de los atributos cuando se complete este metodo
		
		model.addAttribute("usuario", usuario);

		return "redirect:/ver";
	}
	 
	 @GetMapping("ver")
	 public String ver(@SessionAttribute(name="usuario",required = false)Usuario usuario,Model model,SessionStatus status) {
		 
		 if(usuario == null) {
			 return "redirect:/form";
		 }
		 
		 model.addAttribute("titulo", "Valores del formulario");
		 status.setComplete();
		 return "resultado";
	 }
}
