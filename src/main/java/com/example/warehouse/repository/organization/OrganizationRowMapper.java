package com.example.warehouse.repository.organization;

import com.example.warehouse.entity.organization.Organization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationRowMapper implements RowMapper<Organization> {

    @Override
    public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {

        Organization organization = new Organization();

        organization.setId(rs.getString("id"));
        organization.setLogoPath(rs.getString("logo_path"));
        organization.setName(rs.getString("name"));
        organization.setStatus(rs.getShort("status"));
        organization.setDescription(rs.getString("description"));
        organization.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        organization.setCreatedBy(rs.getString("created_by"));
        organization.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        organization.setUpdatedBy(rs.getString("updated_by"));

        return organization;
    }


}
