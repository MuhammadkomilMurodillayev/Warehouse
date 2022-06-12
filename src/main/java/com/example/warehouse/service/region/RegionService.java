package com.example.warehouse.service.region;

import com.example.warehouse.dto.region.RegionDto;
import com.example.warehouse.repository.region.RegionRepository;
import com.example.warehouse.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RegionService implements BaseService {

    private final RegionRepository repository;

    public RegionService(RegionRepository repository) {
        this.repository = repository;
    }

    public List<RegionDto> getAll() {

        return repository.findAllNotDeleted();
    }
}
