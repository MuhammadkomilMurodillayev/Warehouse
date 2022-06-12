package com.example.warehouse.service.product;

import com.example.warehouse.criteria.product.ProductCategoryCriteria;
import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.dto.product.*;
import com.example.warehouse.entity.product.Product;
import com.example.warehouse.entity.product.ProductCategory;
import com.example.warehouse.mapper.product.ProductCategoryMapper;
import com.example.warehouse.repository.product.ProductCategoryRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.validation.product.ProductCategoryValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.getSessionUser;

@Service
public class ProductCategoryService extends
        AbstractService<
                ProductCategoryRepository,
                ProductCategoryMapper,
                ProductCategoryValidator> implements
        BaseCrudService<
                ProductCategoryDto,
                ProductCategoryUpdateDto,
                ProductCategoryCreateDto,
                ProductCategoryCriteria,
                String> {

    protected ProductCategoryService(ProductCategoryRepository repository, ProductCategoryMapper mapper, ProductCategoryValidator validation) {
        super(repository, mapper, validation);
    }

    @Override
    public ProductCategoryDto get(String id) {

        ProductCategory productCategory = repository.findByIdNotDeleted(id);

        return mapper.toDto(productCategory);
    }

    @Override
    public List<ProductCategoryDto> getAll(ProductCategoryCriteria criteria) {
        validation.checkCriteria(criteria);
        List<ProductCategory> productCategories = repository.findAllNotDeleted(criteria);

        return mapper.toDto(productCategories);
    }

    @Override
    public String create(ProductCategoryCreateDto dto) {
        validation.checkCreate(dto);
        ProductCategory productCategory = mapper.fromCreateDto(dto);
        productCategory.setCreatedBy(getSessionUser().getId());
        productCategory.setUpdatedBy(getSessionUser().getId());
        productCategory.setUpdatedAt(productCategory.getCreatedAt());
        return repository.save(productCategory).getId();
    }

    @Override
    public void update(ProductCategoryUpdateDto dto) {
        validation.checkUpdate(dto);
        ProductCategory productCategory = repository.findByIdNotDeleted(dto.getId());
        productCategory.setUpdatedAt(LocalDateTime.now());
        productCategory.setUpdatedBy(getSessionUser().getId());
        repository.save(mapper.fromUpdateDto(productCategory, dto));
    }

    @Override
    public void delete(String id) {
        repository.softDelete(id);
    }
}
