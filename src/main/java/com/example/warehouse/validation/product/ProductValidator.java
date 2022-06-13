package com.example.warehouse.validation.product;

import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.dto.product.ProductCreateDto;
import com.example.warehouse.dto.product.ProductUpdateDto;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.repository.warehouse.WarehouseRepository;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.getSessionUser;
import static com.example.warehouse.config.security.utils.UtilsForSessionUser.hasRole;

@Component
public class ProductValidator
        extends AbstractValidation<ProductCreateDto, ProductUpdateDto, ProductCriteria> {

    private final WarehouseRepository warehouseRepository;

    public ProductValidator(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void checkCreate(ProductCreateDto dto) {


    }

    @Override
    public void checkUpdate(ProductUpdateDto dto) {

    }

    @Override
    public void     checkCriteria(ProductCriteria criteria) {
        if (hasRole(AuthRole.EMPLOYEE))
            criteria.setWarehouseId(warehouseRepository.getWarehouseId(getSessionUser().getId()));
        else
            criteria.setOrganizationId(getSessionUser().getOrganizationId());
    }

    @Override
    public void checkGet(String id) {

    }
}
