package com.example.warehouse.service.product;

import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.dto.organization.OrganizationDto;
import com.example.warehouse.dto.product.ProductCreateDto;
import com.example.warehouse.dto.product.ProductDto;
import com.example.warehouse.dto.product.ProductUpdateDto;
import com.example.warehouse.entity.organization.Organization;
import com.example.warehouse.entity.product.Product;
import com.example.warehouse.mapper.product.ProductMapper;
import com.example.warehouse.repository.product.ProductRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.service.utils.UploadPhotoService;
import com.example.warehouse.validation.product.ProductValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.getSessionUser;

@Service
public class ProductService
        extends AbstractService<
        ProductRepository,
        ProductMapper,
        ProductValidator>
        implements BaseCrudService<
        ProductDto,
        ProductUpdateDto,
        ProductCreateDto,
        ProductCriteria,
        String> {
    private final UploadPhotoService uploadPhotoService;

    protected ProductService(ProductRepository repository, ProductMapper mapper, ProductValidator validation, UploadPhotoService uploadPhotoService) {
        super(repository, mapper, validation);
        this.uploadPhotoService = uploadPhotoService;
    }

    @Override
    public ProductDto get(String id) {

        Product product = repository.findByIdNotDeleted(id);

        ProductDto productDto = mapper.toDto(product);
        productDto.setTotalPrice(product.getPrice() * product.getCount());

        return productDto;
    }

    @Override
    public List<ProductDto> getAll(ProductCriteria criteria) {
        List<Product> products = repository.findAllNotDeleted(criteria);

        return mapper.toDto(products);
    }

    @Override
    public String create(ProductCreateDto dto) {

        validation.checkCreate(dto);
        Product product = mapper.fromCreateDto(dto);
        String imagePath = uploadPhotoService.upload(dto.getImage());
        product.setCreatedBy(getSessionUser().getId());
        product.setUpdatedBy(getSessionUser().getId());
        product.setUpdatedAt(product.getCreatedAt());
        product.setImagePath(imagePath);
        product.setTotalPrice(product.getPrice() * product.getCount());
        return repository.save(product).getId();
    }

    @Override
    public void update(ProductUpdateDto dto) {
        validation.checkUpdate(dto);
        Product product = repository.findByIdNotDeleted(dto.getId());
        product.setUpdatedAt(LocalDateTime.now());
        product.setUpdatedBy(getSessionUser().getId());
        product.setTotalPrice(product.getPrice() * product.getCount());
        repository.save(mapper.fromUpdateDto(product, dto));
    }

    @Override
    public void delete(String id) {
        repository.softDelete(id);
    }

    public List<ProductDto> getAllInOrganization(ProductCriteria criteria) {
        criteria.setOrganizationId(getSessionUser().getOrganizationId());
        return repository.findAllInOrganization(criteria);
    }

    public List<ProductDto> getAllInAllOrganization(ProductCriteria criteria) {

        return repository.findAllInAllOrganization(criteria);
    }
}
