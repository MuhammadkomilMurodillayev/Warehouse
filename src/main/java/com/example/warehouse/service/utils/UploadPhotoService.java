package com.example.warehouse.service.utils;

import com.example.warehouse.properties.FileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class UploadPhotoService {

    private final FileProperties fileProperties;

    @Autowired
    public UploadPhotoService(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    public String upload(MultipartFile logo) {

        String format = StringUtils.getFilenameExtension(logo.getOriginalFilename());
        String contentType = logo.getContentType();

        assert contentType != null;
        String encodeData = Base64.getEncoder().encodeToString(contentType.getBytes());
        String fileGeneratedName = encodeData + UUID.randomUUID().toString().replace("-", "");

        String photoPath = fileProperties.getOrganizationLogoRootPath() + fileGeneratedName + "." + format;
        Path path = Paths.get(photoPath);
        try {
            Files.copy(logo.getInputStream(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoPath;
    }
}
