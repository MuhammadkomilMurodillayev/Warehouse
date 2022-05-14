package com.example.warehouse.service.product;

import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.dto.product.ProductCreateDto;
import com.example.warehouse.dto.product.ProductDto;
import com.example.warehouse.dto.product.ProductUpdateDto;
import com.example.warehouse.service.BaseCrudService;

import java.util.List;

public class ProductService
        implements BaseCrudService<
                ProductDto,
                ProductUpdateDto,
                ProductCreateDto,
                ProductCriteria,
                String> {
    @Override
    public ProductDto get(String id) {
        return null;
    }

    @Override
    public List<ProductDto> getAll(ProductCriteria criteria) {
        return null;
    }

    @Override
    public String create(ProductCreateDto dto) {
        return null;
    }

    @Override
    public void update(ProductUpdateDto dto) {

    }

    @Override
    public void delete(String id) {

    }
}
