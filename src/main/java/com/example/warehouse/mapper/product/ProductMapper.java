package com.example.warehouse.mapper.product;

import com.example.warehouse.dto.product.ProductCreateDto;
import com.example.warehouse.dto.product.ProductDto;
import com.example.warehouse.dto.product.ProductUpdateDto;
import com.example.warehouse.entity.product.Product;
import com.example.warehouse.mapper.BaseGenericMapper;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper extends BaseGenericMapper<
        Product,
        ProductDto,
        ProductCreateDto,
        ProductUpdateDto> {

    @Override
    Product fromCreateDto(ProductCreateDto dto);

    @Override
    Product fromUpdateDto(@MappingTarget Product product, ProductUpdateDto dto);

    @Override
    ProductDto toDto(Product entity);

    @Override
    List<ProductDto> toDto(List<Product> entities);
}
