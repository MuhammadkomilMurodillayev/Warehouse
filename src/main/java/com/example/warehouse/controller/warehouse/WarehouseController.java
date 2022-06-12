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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.warehouse.controller.AbstractController.PATH;

@RestController
@RequestMapping(PATH + "/warehouse")
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
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/create")
    protected ResponseEntity<DataDto<String>> create(@RequestBody WarehouseCreateDto dto) {
        return new ResponseEntity<>(new DataDto<>(service.create(dto)), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    protected ResponseEntity<DataDto<String>> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(new DataDto<>("deleted"), HttpStatus.OK);
    }


    @Override
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/update/{id}")
    protected ResponseEntity<DataDto<String>> update(@RequestBody WarehouseUpdateDto dto, @PathVariable String id) {
        dto.setId(id);
        service.update(dto);
        return new ResponseEntity<>(new DataDto<>("updated"), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPER_ADMIN','MANAGER')")
    @GetMapping("/get/{id}")
    protected ResponseEntity<DataDto<WarehouseDto>> get(@PathVariable String id) {
        return new ResponseEntity<>(new DataDto<>(service.get(id)), HttpStatus.OK);
    }


    @Override
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPER_ADMIN','MANAGER')")
    @GetMapping("/getAll")
    protected ResponseEntity<DataDto<List<WarehouseDto>>> getAll(WarehouseCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(service.getAll(criteria)), HttpStatus.OK);
    }
}
