package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.services.ClienteService;
import com.example.springboot.app.models.entity.Cliente;
import com.example.springboot.app.util.paginator.PageRender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Controller
@SessionAttributes("cliente")
@Slf4j
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    private final static String  UPLOADS_FOLDER = "uploads";


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
        Path pathFoto = Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
        log.info("pathFoto: "+pathFoto);
        Resource recurso = null;
        try {
                recurso = new UrlResource(pathFoto.toUri());
                if(!recurso.exists() || !recurso.isReadable()){
                    throw new RuntimeException("Error: no se puede cargar la imagen: "+pathFoto.toString());
                }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
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

        if(!foto.isEmpty()){
            //Obtenos la ruta del directorio donde se almaceneran las imágenes
            //Path directorioRecursos = Paths.get("C://Spring5//workspace//JAVA//spring-boot-data-jpa//src//main//resources//static//uploads");
            //Esto es para guardar las imagenes dentro del proyecto->Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
            //Convertimos la ruta a string para poder manipular la ruta con el archivo para poder almacenar
            //Form parte para guardar las imgenes dentro del proyectoString rootPath = directorioRecursos.toFile().getAbsolutePath();

            //Esta forma esta para almacenar files fuera del proyecto en un ruta estatica dentro de nuestro ordenador
            //String rootPath = "C://Temp//uploads";



            //Proceso para reemplazar foto cuando se edita
            if(cliente.getId() != null && cliente.getId()>0 && cliente.getFoto()!=null && cliente.getFoto().length() > 0){
                Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
                File archivo = rootPath.toFile();

                if(archivo.exists() && archivo.canRead()){
                    archivo.delete();
                }
            }
            //Esta forma es para alamacenar files dentro de nuestro proyecto pero en la raíz
            //Obtenemos un nombre disitinto para renombrar la foto
            String uniqueFilename = UUID.randomUUID().toString()+ "_"+foto.getOriginalFilename();
            //resolve de manera auotmática concatena el nombre del archivo
            Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(uniqueFilename);
            //Obtenemos la ruta absoluta del directorio donde alamacenaremos los files
            Path rootAbsolutePath = rootPath.toAbsolutePath();
            log.info("rootPath: "+rootPath);
            log.info("rootPathAbsolute: "+ rootAbsolutePath);

            try{
                //obtenemos los bytes del archivo
                //byte[] bytes = foto.getBytes();
                /** Esta es una forma de hacrlo **/
                //obtenemos el nombre original del archivo y lo concatenamos con la ruta string del directorio
                //Path rutaCompleta = Paths.get(rootPath+"//"+foto.getOriginalFilename());
                //creamos el archivo en le directorio especificado
                //Files.write(rutaCompleta,bytes);
                /** Esta es otra manera de hacerlo con Files.copy**/
                //Copiamos el archivo al directorio
                Files.copy(foto.getInputStream(),rootAbsolutePath);
                //mensaje
                flash.addFlashAttribute("info", "Has subido correctamente '"+uniqueFilename+"'");
                //setteo de nombre la foto para que se alamacene en el DB
                cliente.setFoto(uniqueFilename);

            }catch (IOException e){
                e.printStackTrace();
            }

        }

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

            Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
            File archivo = rootPath.toFile();

            if(archivo.exists() && archivo.canRead()){
                if (archivo.delete()){
                    flash.addFlashAttribute("info","Foto "+cliente.getFoto()+"eliminada con exito!");
                }
            }
        }

        return "redirect:/listar";
    }
}
