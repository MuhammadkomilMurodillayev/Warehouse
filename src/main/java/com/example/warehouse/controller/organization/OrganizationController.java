package com.example.warehouse.controller.organization;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.repository.organization.OrganizationRepository;
import com.example.warehouse.service.organization.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
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
    private final OrganizationRepository repository;

    public OrganizationController(OrganizationService service, OrganizationRepository repository) {
        super(service);
        this.repository = repository;
    }
    @Operation(summary = "create organization")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    protected ResponseEntity<DataDto<String>> create(@RequestParam("name") String name,
                                                     @RequestParam(value = "description", defaultValue = "") String description,
                                                     @RequestParam("logo") MultipartFile logo) {
        OrganizationCreateDto dto = new OrganizationCreateDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setLogo(logo);

        return new ResponseEntity<>(new DataDto<>(service.create(dto)), HttpStatus.OK);

    }

    @Operation(summary = "delete organization")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Override
    @DeleteMapping("/delete/{id}")
    protected ResponseEntity<DataDto<String>> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(new DataDto<>("deleted"), HttpStatus.OK);
    }

    @Operation(summary = "update organization", description = "null data ketmasin")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    protected ResponseEntity<DataDto<String>> update(@PathVariable String id,
                                                     @RequestParam("name") String name,
                                                     @RequestParam(value = "description", defaultValue = "") String description,
                                                     @RequestParam("logo") MultipartFile logo) {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(name, description, logo);
        dto.setId(id);
        service.update(dto);

        return new ResponseEntity<>(new DataDto<>("updated"), HttpStatus.OK);
    }

    @Override
    @Operation(summary = "organization info")
    @GetMapping("/get/{id}")
    protected ResponseEntity<DataDto<OrganizationDto>> get(@PathVariable String id) {
        return new ResponseEntity<>(new DataDto<>(service.get(id)), HttpStatus.OK);
    }

    @Operation(summary = "get organization logo")
    @GetMapping("getLogo/{fileName}")
    protected void getLogo(@PathVariable(name = "fileName") String name, HttpServletResponse response) throws FileNotFoundException {
        service.getLogo(name,response);
    }

    @Override
    @Operation(summary = "get organizations", description = "all organization in server")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @GetMapping("/getAll")
    protected ResponseEntity<DataDto<List<OrganizationDto>>> getAll(OrganizationCriteria criteria) {
        List<OrganizationDto> organizationList = service.getAll(criteria);
        Integer totalData = organizationList.size();
        Long allData = repository.allDataCount();
        return new ResponseEntity<>(new DataDto<>(organizationList, totalData, allData), HttpStatus.OK);
    }

    @Operation(summary = "block organization", description = "<br>status = 0 -> block <br>status = 1 -> active")
    @PutMapping("/block/{id}")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    protected ResponseEntity<DataDto<String>> block(@PathVariable String id) {
        service.block(id);
        return new ResponseEntity<>(new DataDto<>("blocked"), HttpStatus.OK);
    }

    @Operation(summary = "unblock organization", description = "<br>status = 0 -> block <br>status = 1 -> active")
    @PutMapping("/unBlock/{id}")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    protected ResponseEntity<DataDto<String>> unBlock(@PathVariable String id) {
        service.unBlock(id);
        return new ResponseEntity<>(new DataDto<>("activated"), HttpStatus.OK);
    }


    @Deprecated
    @Override
    protected ResponseEntity<DataDto<String>> create(OrganizationCreateDto dto) {
        return null;
    }

    @Deprecated
    @Override
    protected ResponseEntity<DataDto<String>> update(@RequestBody OrganizationUpdateDto dto) {
        return null;
    }
}
