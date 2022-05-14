package com.example.warehouse.controller.organization;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.error.AppErrorDto;
import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.service.organization.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization/")
public class OrganizationController
        extends AbstractController<
        OrganizationService,
        OrganizationDto,
        OrganizationCreateDto,
        OrganizationUpdateDto,
        String,
        OrganizationCriteria> {

    @Override
    @PostMapping("create")
    protected ResponseEntity<DataDto<String>> create(@RequestBody OrganizationCreateDto dto) {
        return new ResponseEntity<>(new DataDto<>(service.create(dto)),HttpStatus.OK);

    }

    @Override
    @DeleteMapping("delete/{id}")
    protected ResponseEntity<DataDto<Void>> delete(@PathVariable String id) {
        return null;
    }

    @Override
    @PutMapping("update")
    protected ResponseEntity<DataDto<Void>> update(@RequestBody OrganizationUpdateDto dto) {
        return null;
    }

    @Override
    @GetMapping("get/{id}")
    protected ResponseEntity<DataDto<OrganizationDto>> get(@PathVariable String id) {
        return null;
    }

    @Override
    @GetMapping("getAll")
    protected ResponseEntity<DataDto<List<OrganizationDto>>> getAll(@RequestBody OrganizationCriteria criteria) {
        return null;
    }
}
