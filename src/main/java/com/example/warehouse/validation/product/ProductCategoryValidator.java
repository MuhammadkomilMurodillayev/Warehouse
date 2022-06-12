package com.example.warehouse.validation.product;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.criteria.product.ProductCategoryCriteria;
import com.example.warehouse.dto.product.ProductCategoryCreateDto;
import com.example.warehouse.dto.product.ProductCategoryUpdateDto;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.exception.BlockException;
import com.example.warehouse.exception.PermissionDenied;
import com.example.warehouse.repository.product.ProductCategoryRepository;
import com.example.warehouse.service.organization.OrganizationService;
import com.example.warehouse.validation.AbstractValidation;
import org.springframework.stereotype.Component;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.*;

@Component
public class ProductCategoryValidator
        extends AbstractValidation<ProductCategoryCreateDto, ProductCategoryUpdateDto, ProductCategoryCriteria> {

    private final ProductCategoryRepository productCategoryRepository;
    private final OrganizationService organizationService;

    public ProductCategoryValidator(ProductCategoryRepository productCategoryRepository, OrganizationService organizationService) {
        this.productCategoryRepository = productCategoryRepository;
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
        if (criteria.getWarehouseId() == null || criteria.getOrganizationId() == null && (!hasAnyRole(AuthRole.ADMIN, AuthRole.SUPER_ADMIN)))
            throw new PermissionDenied();
    }

    @Override
    public void checkGet(String id) {

    }
}
