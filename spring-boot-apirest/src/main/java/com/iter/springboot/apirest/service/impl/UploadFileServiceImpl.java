package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {
    @Override
    public Resource load(String filename) {
        return null;
    }

    @Override
    public String copy(MultipartFile file) {
        if(!file.isEmpty()){
            String uniqueFilename = UUID.randomUUID().toString()+ "_"+file.getOriginalFilename();
            String rootPath = "C:/Spring5/frontend/angular/angular/clientes-app/src/assets/uplodas";
            Path rootPathComplete = Paths.get(rootPath+"/"+uniqueFilename);
            try{
                Files.copy(file.getInputStream(), rootPathComplete);
            }catch (IOException e){
                e.printStackTrace();
            }
            return uniqueFilename;
        }
            return null;
    }

    @Override
    public boolean delete(String filename) {
        return false;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void init() {

    }
}
