package com.example.springboot.app.dao.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

    //Método para cargar un recurso en este caso una imagen
    public Resource load(String filename);

    //Método para retornar el nombre de la imagen que sea unico
    public String copy(MultipartFile file);

    //Método para eliminar la imagen
    public boolean delete(String filename);

    void deleteAll();

    void init();
}
