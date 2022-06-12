package com.example.warehouse.validation.product;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.dto.product.ProductCreateDto;
import com.example.warehouse.dto.product.ProductUpdateDto;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator
        extends AbstractValidation<ProductCreateDto, ProductUpdateDto,ProductCriteria> {

    @Override
    public void checkCreate(ProductCreateDto dto) {

    }

    @Override
    public void checkUpdate(ProductUpdateDto dto) {

    }

    @Override
    public void checkCriteria(ProductCriteria criteria) {

    }

    @Override
    public void checkGet(String id) {

    }
}
