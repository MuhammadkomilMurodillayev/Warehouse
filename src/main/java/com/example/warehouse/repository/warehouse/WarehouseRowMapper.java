package com.example.warehouse.repository.warehouse;

import com.example.warehouse.entity.warehouse.Warehouse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WarehouseRowMapper implements RowMapper<Warehouse> {
    @Override
    public Warehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
        Warehouse warehouse = new Warehouse();

        warehouse.setId(rs.getString("id"));
        warehouse.setName(rs.getString("name"));
        warehouse.setLatitude(rs.getDouble("latitude"));
        warehouse.setLongitude(rs.getDouble("longitude"));
        warehouse.setCreatedBy(rs.getString("created_by"));
        warehouse.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        warehouse.setUpdatedBy(rs.getString("updated_by"));
        warehouse.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        warehouse.setStatus(rs.getShort("status"));
        warehouse.setRegionId(rs.getString("region_id"));
        warehouse.setOrganizationId(rs.getString("organization_id"));

        return warehouse;
    }
}
