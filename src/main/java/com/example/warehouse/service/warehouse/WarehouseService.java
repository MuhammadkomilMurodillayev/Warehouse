package com.example.warehouse.service.warehouse;

import com.example.warehouse.criteria.warehouse.WarehouseCriteria;
import com.example.warehouse.dto.warehouse.WarehouseCreateDto;
import com.example.warehouse.dto.warehouse.WarehouseDto;
import com.example.warehouse.dto.warehouse.WarehouseUpdateDto;
import com.example.warehouse.entity.warehouse.Warehouse;
import com.example.warehouse.mapper.warehouse.WarehouseMapper;
import com.example.warehouse.repository.warehouse.WarehouseRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.validation.warehouse.WarehouseValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.getSessionUser;

@Service
public class WarehouseService
        extends AbstractService<
        WarehouseRepository,
        WarehouseMapper,
        WarehouseValidation>
        implements BaseCrudService<
        WarehouseDto,
        WarehouseUpdateDto,
        WarehouseCreateDto,
        WarehouseCriteria,
        String> {

    public WarehouseService(WarehouseRepository repository, WarehouseMapper mapper, WarehouseValidation validation) {
        super(repository, mapper, validation);
    }

    @Override
    public WarehouseDto get(String id) {

        Warehouse warehouse = repository.findByIdNotDeleted(id);

        return mapper.toDto(warehouse);
    }

    @Override
    public List<WarehouseDto> getAll(WarehouseCriteria criteria) {
        List<Warehouse> warehouses = repository.findAllNotDeleted(criteria);

        return mapper.toDto(warehouses);
    }

    @Override
    public String create(WarehouseCreateDto dto) {
        validation.checkCreate(dto);
        Warehouse warehouse = mapper.fromCreateDto(dto);
        warehouse.setCreatedBy(getSessionUser().getId());
        warehouse.setUpdatedBy(getSessionUser().getId());
        warehouse.setUpdatedAt(warehouse.getCreatedAt());
        return repository.save(warehouse).getId();
    }

    @Override
    public void update(WarehouseUpdateDto dto) {
        validation.checkUpdate(dto);
        Warehouse warehouse = repository.findByIdNotDeleted(dto.getId());
        warehouse.setUpdatedAt(LocalDateTime.now());
        warehouse.setUpdatedBy(getSessionUser().getId());
        repository.save(mapper.fromUpdateDto(warehouse, dto));
    }

    @Override
    public void delete(String id) {
        repository.softDelete(id);
    }
}
