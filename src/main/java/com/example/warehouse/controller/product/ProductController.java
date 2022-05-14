package com.example.warehouse.controller.product;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.product.ProductCreateDto;
import com.example.warehouse.dto.product.ProductDto;
import com.example.warehouse.dto.product.ProductUpdateDto;
import com.example.warehouse.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/")
public class ProductController extends AbstractController<
        ProductService,
        ProductDto,
        ProductCreateDto,
        ProductUpdateDto,
        String,
        ProductCriteria> {

    @Override
    @PostMapping("create")
    protected ResponseEntity<DataDto<String>> create(@RequestBody ProductCreateDto dto) {
        return null;
    }

    @Override
    @DeleteMapping("delete/{id}")
    protected ResponseEntity<DataDto<Void>> delete(@PathVariable String id) {
        return null;
    }

    @Override
    @PutMapping("update")
    protected ResponseEntity<DataDto<Void>> update(@RequestBody ProductUpdateDto dto) {
        return null;
    }

    @Override
    @GetMapping("get/{id}")
    protected ResponseEntity<DataDto<ProductDto>> get(@RequestBody String id) {
        return null;
    }

    @Override
    @GetMapping("getAll")
    protected ResponseEntity<DataDto<List<ProductDto>>> getAll(@RequestBody ProductCriteria criteria) {
        return null;
    }
}
