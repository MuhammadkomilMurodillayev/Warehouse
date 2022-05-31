package com.example.warehouse.controller.product;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.product.ProductCreateDto;
import com.example.warehouse.dto.product.ProductDto;
import com.example.warehouse.dto.product.ProductUpdateDto;
import com.example.warehouse.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.warehouse.controller.AbstractController.PATH;

@RestController
@RequestMapping(PATH +"/product")
public class ProductController extends AbstractController<
        ProductService,
        ProductDto,
        ProductCreateDto,
        ProductUpdateDto,
        String,
        ProductCriteria> {

    public ProductController(ProductService service) {
        super(service);
    }

    @Override
    @PostMapping("/create")
    protected ResponseEntity<DataDto<String>> create(@RequestBody ProductCreateDto dto) {
        return new ResponseEntity<>(new DataDto<>(service.create(dto)), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    protected ResponseEntity<DataDto<String>> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(new DataDto<>("deleted"),HttpStatus.OK);
    }

    @Override
    @PutMapping("/update")
    protected ResponseEntity<DataDto<String>> update(@RequestBody ProductUpdateDto dto) {
        service.update(dto);
        return new ResponseEntity<>(new DataDto<>("updated"),HttpStatus.OK);
    }

    @Override
    @GetMapping("get/{id}")
    protected ResponseEntity<DataDto<ProductDto>> get(@PathVariable String id) {
        return new ResponseEntity<>(new DataDto<>(service.get(id)), HttpStatus.OK);
    }

    @Override
    @GetMapping("getAll")
    protected ResponseEntity<DataDto<List<ProductDto>>> getAll(@RequestBody ProductCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(service.getAll(criteria)), HttpStatus.OK);

    }
}
