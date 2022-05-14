package com.example.warehouse.dto.organization;

import com.example.warehouse.dto.BaseDto;
import org.springframework.web.multipart.MultipartFile;

public class OrganizationCreateDto implements BaseDto {

    private String name;

    private String description;

    private MultipartFile logo;

}
