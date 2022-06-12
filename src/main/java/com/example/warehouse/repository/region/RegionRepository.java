package com.example.warehouse.repository.region;

import com.example.warehouse.dto.region.RegionDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegionRepository {
    private final JdbcTemplate jdbcTemplate;

    public RegionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RegionDto> findAllNotDeleted() {
        return jdbcTemplate.query("select * from region where not deleted order by created_at", (rs, rowNum) ->
                new RegionDto(rs.getString("id"),
                        rs.getString("name")));
    }
}
