package com.example.warehouse.validation.product;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.product.ProductCategoryCreateDto;
import com.example.warehouse.dto.product.ProductCategoryUpdateDto;
import com.example.warehouse.repository.product.ProductCategoryRepository;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryValidator
        extends AbstractValidation<ProductCategoryCreateDto, ProductCategoryUpdateDto> {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryValidator(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public void checkCreate(ProductCategoryCreateDto dto) {
        productCategoryRepository.checkCategoryName(dto.getName(),dto.getOrganizationId());
    }

    @Override
    public void checkUpdate(ProductCategoryUpdateDto dto) {

    }

    @Override
    public void checkCriteria(UserCriteria criteria) {

    }
}
