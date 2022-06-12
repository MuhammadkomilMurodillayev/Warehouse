package com.example.warehouse.controller.product;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.product.ProductCategoryCriteria;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.product.ProductCategoryCreateDto;
import com.example.warehouse.dto.product.ProductCategoryDto;
import com.example.warehouse.dto.product.ProductCategoryUpdateDto;
import com.example.warehouse.service.product.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.warehouse.controller.AbstractController.PATH;

@RestController
@RequestMapping(PATH + "/productCategory")
public class ProductCategoryController
        extends AbstractController<
        ProductCategoryService,
        ProductCategoryDto,
        ProductCategoryCreateDto,
        ProductCategoryUpdateDto,
        String,
        ProductCategoryCriteria> {

    public ProductCategoryController(ProductCategoryService service) {
        super(service);
    }

    @Override
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','ADMIN','MANAGER')")
    @PostMapping("/create")
    protected ResponseEntity<DataDto<String>> create(@RequestBody ProductCategoryCreateDto dto) {
        return new ResponseEntity<>(new DataDto<>(service.create(dto)), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','ADMIN','MANAGER')")
    @DeleteMapping("/delete/{id}")
    protected ResponseEntity<DataDto<String>> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(new DataDto<>("deleted"), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','ADMIN','MANAGER')")
    @PutMapping("/update/{id}")
    protected ResponseEntity<DataDto<String>> update(@RequestBody ProductCategoryUpdateDto dto, @PathVariable String id) {

        dto.setId(id);
        service.update(dto);
        return new ResponseEntity<>(new DataDto<>("updated"), HttpStatus.OK);
    }

    @Override
    @GetMapping("/get/{id}")
    protected ResponseEntity<DataDto<ProductCategoryDto>> get(@PathVariable String id) {
        return new ResponseEntity<>(new DataDto<>(service.get(id)), HttpStatus.OK);
    }

    @Override
    @GetMapping("/getAll")
    protected ResponseEntity<DataDto<List<ProductCategoryDto>>> getAll(ProductCategoryCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(service.getAll(criteria)), HttpStatus.OK);
    }
}
