package com.example.warehouse.repository.organization;

import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.entity.auth.User;
import com.example.warehouse.entity.organization.Organization;
import com.example.warehouse.properties.query.OrganizationQueryProperties;
import com.example.warehouse.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRepository
        implements AbstractRepository<
        OrganizationCriteria,
        Organization,
        String> {

    private final JdbcTemplate jdbcTemplate;

    private final OrganizationQueryProperties queryProperties;

    @Autowired
    public OrganizationRepository(JdbcTemplate jdbcTemplate, OrganizationQueryProperties queryProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryProperties = queryProperties;
    }

    @Override
    public Organization save(Organization entity) {
        int response = jdbcTemplate.update(
                "insert into organization(id,name,description,logo_path,created_at,created_by) " +
                        "values(?,?,?,?,?,?)",
                new Object[]{
                        entity.getId(),
                        entity.getName(),
                        entity.getDescription(),
                        entity.getLogoPath(),
                        entity.getCreatedAt(),
                        entity.getCreatedBy()
                }
        );
        if (response > 0)
            return entity;
        else throw new RuntimeException("error");
    }

    @Override
    public Organization findByIdNotDeleted(String getId) {
        return jdbcTemplate.queryForObject(
                "select o.* from organization o where not deleted and o.id = ?",
                BeanPropertyRowMapper.newInstance(Organization.class), getId);
    }
}
