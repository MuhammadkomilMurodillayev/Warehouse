package com.example.warehouse.controller.organization;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.service.organization.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.warehouse.controller.AbstractController.PATH;

@RestController
@RequestMapping(PATH + "/organization")
public class OrganizationController
        extends AbstractController<
        OrganizationService,
        OrganizationDto,
        OrganizationCreateDto,
        OrganizationUpdateDto,
        String,
        OrganizationCriteria> {


    public OrganizationController(OrganizationService service) {
        super(service);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    protected ResponseEntity<DataDto<String>> create(@RequestParam("name") String name,
                                                     @RequestParam("description") String description,
                                                     @RequestParam("logo") MultipartFile logo) {

        OrganizationCreateDto dto = new OrganizationCreateDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setLogo(logo);
        return new ResponseEntity<>(new DataDto<>(service.create(dto)), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    protected void delete(@PathVariable String id) {
        service.delete(id);
    }

    @Override
    @PutMapping("/update")
    protected void update(@RequestBody OrganizationUpdateDto dto) {
        service.update(dto);
    }

    @Override
    @GetMapping("/get/{id}")
    protected ResponseEntity<DataDto<OrganizationDto>> get(@PathVariable String id) {
        return new ResponseEntity<>(new DataDto<>(service.get(id)), HttpStatus.OK);
    }

    @Override
    @GetMapping("/getAll")
    protected ResponseEntity<DataDto<List<OrganizationDto>>> getAll(@RequestBody OrganizationCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(service.getAll(criteria)), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<DataDto<String>> create(OrganizationCreateDto dto) {
        return null;
    }
}
