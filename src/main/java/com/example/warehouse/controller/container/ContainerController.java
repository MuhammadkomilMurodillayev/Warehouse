package com.example.warehouse.controller.container;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.container.ContainerCriteria;
import com.example.warehouse.dto.container.ContainerCreateDto;
import com.example.warehouse.dto.container.ContainerDto;
import com.example.warehouse.dto.container.ContainerUpdateDto;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.service.container.ContainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.warehouse.controller.AbstractController.PATH;

import java.util.List;

@RestController
@RequestMapping(value = PATH + "/container/")
public class ContainerController
        extends AbstractController<
        ContainerService,
        ContainerDto,
        ContainerCreateDto,
        ContainerUpdateDto,
        String,
        ContainerCriteria
        > {


    @Override
    @PostMapping("create")
    protected ResponseEntity<DataDto<String>> create(@RequestBody ContainerCreateDto dto) {
        return null;
    }

    @Override
    @DeleteMapping("delete/{id}")
    protected ResponseEntity<DataDto<Void>> delete(@PathVariable String id) {
        return null;
    }

    @Override
    @PutMapping("update")
    protected ResponseEntity<DataDto<Void>> update(@RequestBody ContainerUpdateDto dto) {
        return null;
    }

    @Override
    @GetMapping("get/{id}")
    protected ResponseEntity<DataDto<ContainerDto>> get(@PathVariable String id) {
        return null;
    }

    @Override
    @GetMapping("getAll")
    protected ResponseEntity<DataDto<List<ContainerDto>>> getAll(@RequestBody ContainerCriteria criteria) {
        return null;
    }
}
