package com.example.warehouse.repository.organization;

import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.entity.organization.Organization;
import com.example.warehouse.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class OrganizationRepository
        implements AbstractRepository<
        OrganizationCriteria,
        Organization,
        String> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Organization save(Organization entity) {
        int response = jdbcTemplate.update(
                "insert into organization(id,name,description,logo_path,created_at,created_by) " +
                        "values(?,?,?,?,?)",
                entity.getId(), entity.getName(), entity.getDescription(), entity.getLogoImagePath(), entity.getCreatedAt(), entity.getCreatedBy()
        );
        if (response == 1)
            return entity;
        else throw new RuntimeException("error");
    }
}
