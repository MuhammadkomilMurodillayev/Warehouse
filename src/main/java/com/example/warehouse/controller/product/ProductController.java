package com.example.warehouse.controller.product;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.product.ProductCreateDto;
import com.example.warehouse.dto.product.ProductDto;
import com.example.warehouse.dto.product.ProductUpdateDto;
import com.example.warehouse.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.warehouse.controller.AbstractController.PATH;

@RestController
@RequestMapping(PATH + "/product")
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

    @Deprecated
    @Override
    protected ResponseEntity<DataDto<String>> create(ProductCreateDto dto) {
        return null;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    protected ResponseEntity<DataDto<String>> create(@RequestParam(name = "name") String name,
                                                     @RequestParam(name = "description") String description,
                                                     @RequestParam(name = "price") Double price,
                                                     @RequestParam(name = "count") Integer count,
                                                     @RequestParam(name = "categoryId") String categoryId,
                                                     @RequestParam(name = "image") MultipartFile image) {
        ProductCreateDto dto = new ProductCreateDto();

        dto.setCategoryId(categoryId);
        dto.setDescription(description);
        dto.setName(name);
        dto.setPrice(price);
        dto.setCount(count);
        dto.setImage(image);

        return new ResponseEntity<>(new DataDto<>(service.create(dto)), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    protected ResponseEntity<DataDto<String>> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(new DataDto<>("deleted"), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    protected ResponseEntity<DataDto<String>> update(@RequestParam(name = "name") String name,
                                                     @RequestParam(name = "description") String description,
                                                     @RequestParam(name = "price") Double price,
                                                     @RequestParam(name = "count") Integer count,
                                                     @RequestParam(name = "categoryId") String categoryId,
                                                     @RequestParam(name = "image") MultipartFile image,
                                                     @PathVariable String id) {

        ProductUpdateDto dto = new ProductUpdateDto();
        dto.setName(name);
        dto.setImage(image);
        dto.setId(id);
        dto.setPrice(price);
        dto.setDescription(description);
        dto.setCategoryId(categoryId);
        dto.setCount(count);
        service.update(dto);
        return new ResponseEntity<>(new DataDto<>("updated"), HttpStatus.OK);

    }

    @Deprecated
    @Override
    protected ResponseEntity<DataDto<String>> update(@RequestBody ProductUpdateDto dto, @PathVariable String id) {
        dto.setId(id);
        service.update(dto);
        return new ResponseEntity<>(new DataDto<>("updated"), HttpStatus.OK);
    }

    @Override
    @GetMapping("get/{id}")
    protected ResponseEntity<DataDto<ProductDto>> get(@PathVariable String id) {
        return new ResponseEntity<>(new DataDto<>(service.get(id)), HttpStatus.OK);
    }

    @Override
    @GetMapping("/getAll")
    protected ResponseEntity<DataDto<List<ProductDto>>> getAll(ProductCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(service.getAll(criteria)), HttpStatus.OK);

    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping("/getAllInOrganization")
    protected ResponseEntity<DataDto<List<ProductDto>>> getAllInOrganization(ProductCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(service.getAllInOrganization(criteria)), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @GetMapping("/getAllInAllOrganization")
    protected ResponseEntity<DataDto<List<ProductDto>>> getAllInAllOrganization(ProductCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(service.getAllInAllOrganization(criteria)), HttpStatus.OK);

    }
}
