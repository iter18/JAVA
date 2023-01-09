package com.example.springboot.app.dao.services.impl;

import com.example.springboot.app.dao.services.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {

    private final static String  UPLOADS_FOLDER = "uploads";

    @Override
    public Resource load(String filename) {
        Resource recurso = null;
        try {
                Path pathFoto = this.getPath(filename);
                 recurso = new UrlResource(pathFoto.toUri());
                if(!recurso.exists() || !recurso.isReadable()){
                    throw new RuntimeException("Error: no se puede cargar la imagen: "+pathFoto.toString());
                }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return recurso;
    }

    @Override
    public String copy(MultipartFile file) {

        if(!file.isEmpty()){
            String uniqueFilename = UUID.randomUUID().toString()+ "_"+file.getOriginalFilename();
            //Obtenos la ruta del directorio donde se almaceneran las imágenes
            //Path directorioRecursos = Paths.get("C://Spring5//workspace//JAVA//spring-boot-data-jpa//src//main//resources//static//uploads");
            //Esto es para guardar las imagenes dentro del proyecto->Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
            //Convertimos la ruta a string para poder manipular la ruta con el archivo para poder almacenar
            //Form parte para guardar las imgenes dentro del proyectoString rootPath = directorioRecursos.toFile().getAbsolutePath();

            //Esta forma esta para almacenar files fuera del proyecto en un ruta estatica dentro de nuestro ordenador
            //String rootPath = "C://Temp//uploads";

            //Esta forma es para alamacenar files dentro de nuestro proyecto pero en la raíz
            //Obtenemos un nombre disitinto para renombrar la foto

            //resolve de manera auotmática concatena el nombre del archivo
            Path rootPath = this.getPath(uniqueFilename);

            log.info("rootPath: "+rootPath);
            log.info("rootPathAbsolute: "+ rootPath);

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
                Files.copy(file.getInputStream(),rootPath);

            }catch (IOException e){
                e.printStackTrace();
            }
            return uniqueFilename;
        }
        return "";
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = this.getPath(filename);
        File archivo = rootPath.toFile();
        boolean elimino = false;
        if(archivo.exists() && archivo.canRead()){
            if (archivo.delete()){
                elimino = true;
            }else {
                elimino = false;
            }
        }
        return elimino;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(Paths.get(UPLOADS_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Path getPath(String filename){
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}
