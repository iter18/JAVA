package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.services.ClienteService;
import com.example.springboot.app.dao.services.UploadFileService;
import com.example.springboot.app.models.entity.Cliente;
import com.example.springboot.app.util.paginator.PageRender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.Map;


@Controller
@SessionAttributes("cliente")
@Slf4j
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    private final static String  UPLOADS_FOLDER = "uploads";

    @Autowired
    private UploadFileService uploadFileService;


    //Método para mostrar todos los resultado sin paginación
    /*@GetMapping(value="/listar")
    public String listar(Model model){
        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",clienteService.findAll());

        return "listar";
    }*/
    //Método para obtener la imágen por petición y poder mostarla
    //al final la expresión.+ es para indicar que se tome en cuanta el archivo
    @GetMapping(value="/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = uploadFileService.load(filename);
        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ recurso.getFilename()+"\"")
                .body(recurso);
    }

    //Método para ver el detalle de un cliente
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable("id")Long id,
                      Map<String,Object> model,
                      RedirectAttributes flash){
        Cliente cliente = clienteService.findById(id);
        if(cliente==null){
            flash.addFlashAttribute("error","El cliente no existe en la base de datos");
            return "redirect:/listar";
        }
        model.put("cliente",cliente);
        model.put("titulo","Detalle del cliente: "+cliente.getNombre());
        return "ver";
    }

    //método para mostrar los registros pero paginados
    @GetMapping(value="/listar")
    public String listar(@RequestParam(name="page", defaultValue = "0") int page, Model model){
        //Le estamos diciendo que muestre 4 registros por página
        Pageable pageRequest = PageRequest.of(page,5);

        Page<Cliente> clientes = clienteService.findAll(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/listar",clientes);

        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",clientes);
        model.addAttribute("page",pageRender);

        return "listar";
    }


    @GetMapping(value = "/form")
    public String crear(Map<String,Object> model){
        Cliente cliente = new Cliente();
        model.put("cliente",cliente);
        model.put("titulo","Formulario cliente");
        return "form";
    }

    @PostMapping(value="/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("titulo","Formulario cliente");
            return "form";
        }

            //Obtenos la ruta del directorio donde se almaceneran las imágenes
            //Path directorioRecursos = Paths.get("C://Spring5//workspace//JAVA//spring-boot-data-jpa//src//main//resources//static//uploads");
            //Esto es para guardar las imagenes dentro del proyecto->Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
            //Convertimos la ruta a string para poder manipular la ruta con el archivo para poder almacenar
            //Form parte para guardar las imgenes dentro del proyectoString rootPath = directorioRecursos.toFile().getAbsolutePath();

            //Esta forma esta para almacenar files fuera del proyecto en un ruta estatica dentro de nuestro ordenador
            //String rootPath = "C://Temp//uploads";


            //Proceso para reemplazar foto cuando se edita
            if(cliente.getId() != null && cliente.getId()>0 && cliente.getFoto()!=null && cliente.getFoto().length() > 0){
               uploadFileService.delete(cliente.getFoto());
            }
            String fileName = uploadFileService.copy(foto);
            flash.addFlashAttribute("info", "Has subido correctamente '"+fileName+"'");
            //setteo de nombre la foto para que se alamacene en el DB
            cliente.setFoto(fileName);



        String mensaje = cliente.getId()!=null ? "Cliente editado con éxisto" : "Cliente creado con éxito!";
        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:listar";
    }

    @RequestMapping(value="/form/{id}")
    public String editar(@PathVariable("id") Long id, Map<String,Object> model,RedirectAttributes flash){
        Cliente cliente = null;
        if(id>0){
            flash.addFlashAttribute("error","El ID del cliente no existe en la BD!");
            cliente = clienteService.findById(id);
        }else{
            flash.addFlashAttribute("error","El ID del cliente no puede ser cero!");
            return "redirect:/listar";
        }
        model.put("cliente",cliente);
        model.put("titulo","Editar cliente");
        return "form";
    }

    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id,RedirectAttributes flash){
        if(id>0){
           Cliente cliente = clienteService.findById(id);
            clienteService.delete(id);
            flash.addFlashAttribute("info","Cliente eliminado con éxito!");

            boolean respuesta = uploadFileService.delete(cliente.getFoto());
            if (respuesta){
                flash.addFlashAttribute("info","Foto "+cliente.getFoto()+"eliminada con exito!");
            }
        }

        return "redirect:/listar";
    }
}
