package com.example.warehouse.controller.warehouse;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.warehouse.WarehouseCriteria;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.warehouse.WarehouseCreateDto;
import com.example.warehouse.dto.warehouse.WarehouseDto;
import com.example.warehouse.dto.warehouse.WarehouseUpdateDto;
import com.example.warehouse.service.warehouse.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse/")
public class WarehouseController extends AbstractController<
        WarehouseService,
        WarehouseDto,
        WarehouseCreateDto,
        WarehouseUpdateDto,
        String,
        WarehouseCriteria> {


    @Override
    @PostMapping("create")
    protected ResponseEntity<DataDto<String>> create(WarehouseCreateDto dto) {
        return null;
    }

    @Override
    @DeleteMapping("delete/{id}")
    protected ResponseEntity<DataDto<Void>> delete(@PathVariable String id) {
        return null;
    }

    @Override
    @PutMapping("update")
    protected ResponseEntity<DataDto<Void>> update(@RequestBody WarehouseUpdateDto dto) {
        return null;
    }

    @Override
    @GetMapping("get/{id}")
    protected ResponseEntity<DataDto<WarehouseDto>> get(@PathVariable String id) {
        return null;
    }

    @Override
    @GetMapping("getAll")
    protected ResponseEntity<DataDto<List<WarehouseDto>>> getAll(@RequestBody WarehouseCriteria criteria) {
        return null;
    }
}
