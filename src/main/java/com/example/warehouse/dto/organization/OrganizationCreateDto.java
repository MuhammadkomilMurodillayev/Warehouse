package com.example.warehouse.dto.organization;

import com.example.warehouse.dto.BaseDto;
import lombok.*;
import org.hibernate.validator.constraints.Normalized;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationCreateDto implements BaseDto {

    @NotBlank(message = "please input organization name")
    private String name;

    private String description;

    @NotBlank(message = "please upload logo image")
    private MultipartFile logo;

}
