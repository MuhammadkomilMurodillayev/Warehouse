package com.example.warehouse.mapper.organization;

import com.example.warehouse.dto.organization.OrganizationCreateDto;
import com.example.warehouse.dto.organization.OrganizationDto;
import com.example.warehouse.dto.organization.OrganizationUpdateDto;
import com.example.warehouse.entity.organization.Organization;
import com.example.warehouse.mapper.BaseGenericMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends BaseGenericMapper<
        Organization,
        OrganizationDto,
        OrganizationCreateDto,
        OrganizationUpdateDto> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Override
    Organization fromCreateDto(OrganizationCreateDto dto);

    @Override
    Organization fromUpdateDto(OrganizationUpdateDto dto);

    @Override
    OrganizationDto toDto(Organization entity);

}
