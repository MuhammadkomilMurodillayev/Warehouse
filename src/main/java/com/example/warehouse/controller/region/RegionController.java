package com.example.warehouse.controller.region;

import com.example.warehouse.controller.BaseController;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.region.RegionDto;
import com.example.warehouse.service.region.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.warehouse.controller.AbstractController.PATH;

@RestController
@RequestMapping(PATH + "/region")
public class RegionController implements BaseController {
    private final RegionService service;

    public RegionController(RegionService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    protected ResponseEntity<DataDto<List<RegionDto>>> getAll() {
        return new ResponseEntity<>(new DataDto<>(service.getAll()), HttpStatus.OK);
    }
}
