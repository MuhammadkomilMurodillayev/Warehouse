package com.example.warehouse.service.organization;

import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.entity.organization.Organization;
import com.example.warehouse.mapper.organization.OrganizationMapper;
import com.example.warehouse.repository.organization.OrganizationRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.service.utils.UploadPhotoService;
import com.example.warehouse.validation.organization.OrganizationValidation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
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

    private final UploadPhotoService uploadPhotoService;

    public OrganizationService(OrganizationRepository repository, OrganizationMapper mapper, OrganizationValidation validation, UploadPhotoService uploadPhotoService) {
        super(repository, mapper, validation);
        this.uploadPhotoService = uploadPhotoService;
    }

    @Override
    public OrganizationDto get(String id) {
        Organization organization = repository.findByIdNotDeleted(id);
        OrganizationDto organizationDto = mapper.toDto(organization);
        organizationDto.setLogoPath(organization.getLogoPath());
        return organizationDto;
    }

    @Override
    public List<OrganizationDto> getAll(OrganizationCriteria criteria) {
        return null;
    }

    @Override
    public String create(OrganizationCreateDto dto) {
        validation.checkCreate(dto);

        Organization organization = mapper.fromCreateDto(dto);
        String logoPath = uploadPhotoService.upload(dto.getLogo());
        organization.setLogoPath(logoPath);

        return repository.save(organization).getId();

    }


    @Override
    public void update(OrganizationUpdateDto dto) {
    }

    @Override
    public void delete(String id) {

    }
}
