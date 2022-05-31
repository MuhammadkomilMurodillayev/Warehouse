package com.example.warehouse.repository.organization;

import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.entity.organization.Organization;
import com.example.warehouse.exception.NotSavedException;
import com.example.warehouse.exception.ObjectNotFoundException;
import com.example.warehouse.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class OrganizationRepository
        implements AbstractRepository<
        OrganizationCriteria,
        Organization,
        String> {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public OrganizationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Organization save(Organization entity) {
        try {
            if (isThere(entity.getId()))
                jdbcTemplate.update(
                        "insert into organization(id,name,description,logo_path,created_at,created_by,updated_at,updated_by, status)" +
                                " values(?,?,?,?,?,?,?,?,?)",
                        entity.getId(),
                        entity.getName(),
                        entity.getDescription(),
                        entity.getLogoPath(),
                        entity.getCreatedAt(),
                        entity.getCreatedBy(),
                        entity.getUpdatedAt(),
                        entity.getUpdatedBy(),
                        entity.getStatus());
            else
                jdbcTemplate.update(
                        "update organization set name=?,description=?,logo_path=?,updated_at=?,updated_by=?, status=? where id=?",
                        entity.getName(),
                        entity.getDescription(),
                        entity.getLogoPath(),
                        entity.getUpdatedAt(),
                        entity.getUpdatedBy(),
                        entity.getStatus(),
                        entity.getId());

        } catch (Exception e) {
            throw new NotSavedException("Organization not saved");
        }
        return entity;
    }

    @Override
    public Organization findByIdNotDeleted(String getId) {
        try {
            return jdbcTemplate.queryForObject(
                    "select o.* from organization o where not deleted and o.id = ?",
                    new OrganizationRowMapper(), getId);

        } catch (Exception e) {
            throw new ObjectNotFoundException("Organization not found", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void softDelete(String deleteId) {
        int deleted = jdbcTemplate.update(
                "update organization set deleted = true where id = ?",
                deleteId);
    }

    @Override
    public List<Organization> findAllNotDeleted(OrganizationCriteria criteria) {
        try {

            return jdbcTemplate.query("select * from organization where not deleted order by created_at limit ? offset ? ",
                    new OrganizationRowMapper(),
                    criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));

        } catch (Exception e) {

            throw new ObjectNotFoundException("empty", Arrays.toString(e.getStackTrace()));
        }
    }

    public void setStatus(short newStatus, String newId) {
        jdbcTemplate.update("update organization set status=? where id=?",
                newStatus, newId);
    }

    @Override
    public Long allDataCount() {
        return jdbcTemplate.queryForObject("select count(*) from organization where not deleted", Long.class);
    }

    public boolean isThere(String valId) {
        Integer val = jdbcTemplate.queryForObject("select count(*) from organization where not deleted and id=?", Integer.class, valId);
        return val == 0;
    }
}
