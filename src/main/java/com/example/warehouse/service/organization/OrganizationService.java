package com.example.warehouse.service.organization;

import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.mapper.organization.OrganizationMapper;
import com.example.warehouse.repository.organization.OrganizationRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.validation.organization.OrganizationValidation;

import java.util.List;

public class OrganizationService
        extends AbstractService<
        OrganizationRepository,
        OrganizationMapper,
        OrganizationValidation>
        implements BaseCrudService<
        OrganizationDto,
        OrganizationUpdateDto,
        OrganizationCreateDto,
        OrganizationCriteria,
        String> {

    @Override
    public OrganizationDto get(String id) {
        return null;
    }

    @Override
    public List<OrganizationDto> getAll(OrganizationCriteria criteria) {
        return null;
    }

    @Override
    public String create(OrganizationCreateDto dto) {
        return null;
    }

    @Override
    public void update(OrganizationUpdateDto dto) {

    }

    @Override
    public void delete(String id) {

    }
}
