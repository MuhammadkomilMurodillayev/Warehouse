package com.example.warehouse.mapper.product;

import com.example.warehouse.dto.product.ProductCategoryCreateDto;
import com.example.warehouse.dto.product.ProductCategoryDto;
import com.example.warehouse.dto.product.ProductCategoryUpdateDto;
import com.example.warehouse.entity.product.ProductCategory;
import com.example.warehouse.mapper.BaseGenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ProductCategoryMapper extends BaseGenericMapper<
        ProductCategory,
        ProductCategoryDto,
        ProductCategoryCreateDto,
        ProductCategoryUpdateDto> {

    @Override
    ProductCategory fromCreateDto(ProductCategoryCreateDto dto);

    @Override
    ProductCategory fromUpdateDto(@MappingTarget ProductCategory productCategory, ProductCategoryUpdateDto dto);

    @Override
    ProductCategoryDto toDto(ProductCategory entity);

    @Override
    List<ProductCategoryDto> toDto(List<ProductCategory> entities);
}
