package com.ecommerce.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Component
public class ImageUpload {
    private final  String UPLOAD_DIR = "C:\\Users\\ashra\\IdeaProjects\\ecommerce_springboot\\admin\\src\\main\\resources\\images";
public Boolean uploadImage(MultipartFile imageProduct) {
    boolean isUpload = false;
    try {

        Files.copy(imageProduct.getInputStream(), Paths.get(UPLOAD_DIR+ File.separator, imageProduct.getOriginalFilename()),
               StandardCopyOption.REPLACE_EXISTING);
        isUpload= true;
    } catch (Exception e) {
        e.printStackTrace();

    }
    return isUpload;
}
    public boolean checkExist(MultipartFile multipartFile){
        boolean isExist = false;
        try {
            File file = new File(UPLOAD_DIR +"\\" + multipartFile.getOriginalFilename());
            isExist = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExist;
    }
}
