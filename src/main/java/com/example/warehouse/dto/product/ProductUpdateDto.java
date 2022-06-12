package com.example.warehouse.dto.product;

import com.example.warehouse.dto.BaseGenericDto;
import com.example.warehouse.entity.product.ProductCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductUpdateDto extends BaseGenericDto {

    @NotBlank
    private String name;

    private String description;

    private Double price;

    private MultipartFile image;

    private Integer count;

    @NotBlank
    private String categoryId;


}
