package com.example.warehouse.dto.organization;

import com.example.warehouse.dto.BaseGenericDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationUpdateDto extends BaseGenericDto {

    private String name;

    private String description;

    private MultipartFile logo;

}
