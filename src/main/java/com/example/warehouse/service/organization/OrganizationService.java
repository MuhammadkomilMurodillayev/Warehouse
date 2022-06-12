package com.example.warehouse.service.organization;

import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationDto;
import com.example.warehouse.dto.organization.OrganizationLogoDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.entity.organization.Organization;
import com.example.warehouse.exception.FileNotFoundException;
import com.example.warehouse.mapper.organization.OrganizationMapper;
import com.example.warehouse.properties.FileProperties;
import com.example.warehouse.repository.organization.OrganizationRepository;
import com.example.warehouse.service.AbstractService;
import com.example.warehouse.service.BaseCrudService;
import com.example.warehouse.service.utils.UploadPhotoService;
import com.example.warehouse.validation.organization.OrganizationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.getSessionUser;

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
    private final FileProperties fileProperties;

    @Autowired
    public OrganizationService(OrganizationRepository repository, OrganizationMapper mapper, OrganizationValidation validation, UploadPhotoService uploadPhotoService, FileProperties fileProperties) {
        super(repository, mapper, validation);
        this.uploadPhotoService = uploadPhotoService;
        this.fileProperties = fileProperties;
    }

    @Override
    public OrganizationDto get(String id) {
        validation.checkGet(id);
        Organization organization = repository.findByIdNotDeleted(id);
        OrganizationDto organizationDto = mapper.toDto(organization);
        organizationDto.setLogoPath(organization.getLogoPath());
        return organizationDto;
    }

    @Override
    public List<OrganizationDto> getAll(OrganizationCriteria criteria) {

        List<Organization> organizations = repository.findAllNotDeleted(criteria);

        return mapper.toDto(organizations);
    }

    @Override
    public String create(OrganizationCreateDto dto) {
        validation.checkCreate(dto);
        Organization organization = mapper.fromCreateDto(dto);
        String logoPath = uploadPhotoService.upload(dto.getLogo());
        organization.setCreatedBy(getSessionUser().getId());
        organization.setUpdatedBy(getSessionUser().getId());
        organization.setUpdatedAt(organization.getCreatedAt());
        organization.setLogoPath(logoPath);
        return repository.save(organization).getId();
    }

    @Override
    public void update(OrganizationUpdateDto dto) {
        validation.checkUpdate(dto);
        Organization organization = repository.findByIdNotDeleted(dto.getId());

        if (dto.getLogo() != null)
            organization.setLogoPath(uploadPhotoService.upload(dto.getLogo()));

        organization.setUpdatedAt(LocalDateTime.now());
        organization.setUpdatedBy(getSessionUser().getId());
        repository.save(mapper.fromUpdateDto(organization, dto));
    }

    @Override
    public void delete(String id) {

        repository.softDelete(id);
    }

    public void block(String id) {
        repository.setStatus((short) 0, id);
    }

    public void unBlock(String id) {
        repository.setStatus((short) 1, id);
    }

    public OrganizationLogoDto getLogo() {

        return repository.getLogo(getSessionUser().getOrganizationId());
    }

    public boolean organizationIsActive(String id) {
        return repository.isActive(id);
    }
}
