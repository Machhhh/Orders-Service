package com.amach.coreServices.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class MultipartFileConverter {

    public File convertToFile(final MultipartFile myFile) throws IOException {
        File convertedFile = new File(myFile.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(myFile.getBytes());
        fos.close();
        return convertedFile;
    }
}
