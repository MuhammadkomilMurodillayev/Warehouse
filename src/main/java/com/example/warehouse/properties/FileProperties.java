package com.example.warehouse.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {

    private String organizationLogoRootPath;

    private String productImageRootPath;
}
