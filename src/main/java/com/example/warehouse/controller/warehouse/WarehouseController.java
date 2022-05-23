package com.example.warehouse.controller.warehouse;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.warehouse.WarehouseCriteria;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.warehouse.WarehouseCreateDto;
import com.example.warehouse.dto.warehouse.WarehouseDto;
import com.example.warehouse.dto.warehouse.WarehouseUpdateDto;
import com.example.warehouse.service.warehouse.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.warehouse.controller.AbstractController.PATH;

@RestController
@RequestMapping(PATH +"/warehouse")
public class WarehouseController extends AbstractController<
        WarehouseService,
        WarehouseDto,
        WarehouseCreateDto,
        WarehouseUpdateDto,
        String,
        WarehouseCriteria> {


    public WarehouseController(WarehouseService service) {
        super(service);
    }

    @Override
    @PostMapping("/")
    protected ResponseEntity<DataDto<String>> create(WarehouseCreateDto dto) {
        return new ResponseEntity<>(new DataDto<>(service.create(dto)), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    protected void delete(@PathVariable String id) {
        service.delete(id);
    }

    @Override
    @PutMapping("/")
    protected void update(@RequestBody WarehouseUpdateDto dto) {
        service.update(dto);
    }

    @Override
    @GetMapping("/{id}")
    protected ResponseEntity<DataDto<WarehouseDto>> get(@PathVariable String id) {
        return new ResponseEntity<>(new DataDto<>(service.get(id)), HttpStatus.OK);
    }

    @Override
    @GetMapping("/")
    protected ResponseEntity<DataDto<List<WarehouseDto>>> getAll(@RequestBody WarehouseCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(service.getAll(criteria)), HttpStatus.OK);
    }
}
