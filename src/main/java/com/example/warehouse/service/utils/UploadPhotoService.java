package com.example.warehouse.service.utils;

import com.example.warehouse.properties.FileProperties;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
public class UploadPhotoService {

    private final FileProperties fileProperties;

    @Autowired
    public UploadPhotoService(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

//    public String upload(MultipartFile logo) {
//
//        String format = StringUtils.getFilenameExtension(logo.getOriginalFilename());
//        String contentType = logo.getContentType();
//
//        assert contentType != null;
//        String encodeData = Base64.getEncoder().encodeToString(contentType.getBytes());
//        String fileGeneratedName = encodeData + UUID.randomUUID().toString().replace("-", "");
//
//        String photoPath = fileProperties.getOrganizationLogoRootPath() + fileGeneratedName + "." + format;
//        Path path = Paths.get(photoPath);
//        try {
//            Files.copy(logo.getInputStream(), path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return photoPath;
//    }

    public String upload(MultipartFile logo) {

        String format = StringUtils.getFilenameExtension(logo.getOriginalFilename());
        String contentType = logo.getContentType();

        assert contentType != null;
        String encodeData = Base64.getEncoder().encodeToString(contentType.getBytes());
        String fileGeneratedName = encodeData + UUID.randomUUID().toString().replace("-", "") + "." + format;

        String photoPath = fileProperties.getOrganizationLogoRootPath() + fileGeneratedName;
        File file = null;
        try {
            file = File.createTempFile(photoPath, "." + format);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert file != null;
            try (FileOutputStream fos = new FileOutputStream(file)) {

                fos.write(logo.getBytes());
                return uploadToGoogleCloud(file, fileGeneratedName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String uploadToGoogleCloud(File file, String fileName) {

        BlobId blobId = BlobId.of("warehose-cf7de.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials;
        try {
            credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/firebase.json"));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/warehose-cf7de.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

}
