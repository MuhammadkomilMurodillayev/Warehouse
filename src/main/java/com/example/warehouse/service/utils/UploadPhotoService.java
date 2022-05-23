package com.example.warehouse.service.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadPhotoService {

    private String UPLOAD_DIRECTORY = "src/main/resources/organizationLogo/";

    public String upload(MultipartFile logo) {

        String format = StringUtils.getFilenameExtension(logo.getOriginalFilename());
        String photoPath = UPLOAD_DIRECTORY + UUID.randomUUID().toString().replace("-", "") + "." + format;
        Path path = Paths.get(photoPath);
        try {
            Files.copy(logo.getInputStream(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoPath;
    }
}
