package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.services.ClienteOptimoService;
import com.example.springboot.app.dao.services.UploadFileService;
import com.example.springboot.app.models.entity.Client;
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
@RequestMapping("/cliente")
@SessionAttributes("client")
@Slf4j
public class ClienteOptimoController {

    @Autowired
    private ClienteOptimoService clienteOptimoService;

    private final static String  UPLOADS_FOLDER = "uploads";

    @Autowired
    private UploadFileService uploadFileService;


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
        Client cliente = clienteOptimoService.findById(id);

        if(cliente==null){
            flash.addFlashAttribute("error","El cliente no existe en la base de datos");
            return "redirect:/cliente/listar";
        }
        model.put("client",cliente);
        model.put("titulo","Detalle del cliente: "+cliente.getNombre());
        return "ver";
    }

    //método para mostrar los registros pero paginados
    @GetMapping(value={"/listar","/"})
    public String listar(@RequestParam(name="page", defaultValue = "0") int page, Model model){
        //Le estamos diciendo que muestre 4 registros por página
        Pageable pageRequest = PageRequest.of(page,5);

        Page<Client> clientes = clienteOptimoService.findAll(pageRequest);

        PageRender<Client> pageRender = new PageRender<>("/cliente/listar",clientes);

        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",clientes);
        model.addAttribute("page",pageRender);

        return "listar";
    }


    @GetMapping(value = "/form")
    public String crear(Map<String,Object> model){
        Client cliente = new Client();
        model.put("client",cliente);
        model.put("titulo","Formulario cliente");
        return "form";
    }

    //Método para guardar
    @PostMapping(value="/form")
    public String guardar(@Valid Client cliente,
                          BindingResult result,
                          Model model,
                          @RequestParam("file") MultipartFile foto,
                          RedirectAttributes flash,
                          SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo","Formulario cliente");
            return "form";
        }

        String mensaje = cliente.getId()!=null ? "Cliente editado con éxisto" : "Cliente creado con éxito!";
        clienteOptimoService.guardar(cliente,foto);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:/cliente/listar";
    }

    @RequestMapping(value="/form/{id}")
    public String editar(@PathVariable("id") Long id, Map<String,Object> model,RedirectAttributes flash){
        Client cliente = null;
        if(id>0){
            flash.addFlashAttribute("error","El ID del cliente no existe en la BD!");
            cliente = clienteOptimoService.findById(id);
        }else{
            flash.addFlashAttribute("error","El ID del cliente no puede ser cero!");
            return "redirect:/cliente/listar";
        }
        model.put("client",cliente);
        model.put("titulo","Editar cliente");
        return "form";
    }

    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id,RedirectAttributes flash){
        boolean respuesta = clienteOptimoService.delete(id);
            if (respuesta){
                flash.addFlashAttribute("info","Datos eliminados por completo exitosamente!");
            }
        return "redirect:/cliente/listar";
    }


}
