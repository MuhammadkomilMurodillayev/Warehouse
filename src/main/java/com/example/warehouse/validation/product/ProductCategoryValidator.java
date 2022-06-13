package com.example.warehouse.validation.product;

import com.example.warehouse.criteria.product.ProductCategoryCriteria;
import com.example.warehouse.dto.product.ProductCategoryCreateDto;
import com.example.warehouse.dto.product.ProductCategoryUpdateDto;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.exception.BlockException;
import com.example.warehouse.repository.product.ProductCategoryRepository;
import com.example.warehouse.repository.warehouse.WarehouseRepository;
import com.example.warehouse.service.organization.OrganizationService;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.*;

@Component
public class ProductCategoryValidator
        extends AbstractValidation<ProductCategoryCreateDto, ProductCategoryUpdateDto, ProductCategoryCriteria> {

    private final ProductCategoryRepository productCategoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final OrganizationService organizationService;

    public ProductCategoryValidator(ProductCategoryRepository productCategoryRepository, WarehouseRepository warehouseRepository, OrganizationService organizationService) {
        this.productCategoryRepository = productCategoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.organizationService = organizationService;
    }

    @Override
    public void checkCreate(ProductCategoryCreateDto dto) {
        productCategoryRepository.checkCategoryName(dto.getName(), dto.getWarehouseId());
        if (!organizationService.organizationIsActive(getSessionUser().getOrganizationId())) {
            throw new BlockException("Forbidden. Because,your organization is blocked");
        }
    }


    @Override
    public void checkUpdate(ProductCategoryUpdateDto dto) {

    }

    @Override
    public void checkCriteria(ProductCategoryCriteria criteria) {
        if (hasRole(AuthRole.EMPLOYEE))
            criteria.setWarehouseId(warehouseRepository.getWarehouseId(getSessionUser().getId()));
    }

    @Override
    public void checkGet(String id) {

    }
}
