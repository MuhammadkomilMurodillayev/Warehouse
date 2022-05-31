package com.example.warehouse.repository.warehouse;

import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.criteria.warehouse.WarehouseCriteria;
import com.example.warehouse.entity.product.Product;
import com.example.warehouse.entity.warehouse.Warehouse;
import com.example.warehouse.exception.NotSavedException;
import com.example.warehouse.exception.ObjectNotFoundException;
import com.example.warehouse.repository.AbstractRepository;
import com.example.warehouse.repository.BaseRepository;
import com.example.warehouse.repository.organization.OrganizationRowMapper;
import com.example.warehouse.repository.product.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class WarehouseRepository implements AbstractRepository<
        WarehouseCriteria,
        Warehouse,
        String> {

    private final JdbcTemplate jdbcTemplate;

    public WarehouseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Warehouse save(Warehouse entity) {

        int saved;
        try {
            if (isThere(entity.getId())) {
                saved = jdbcTemplate.update(
                        "insert into main.warehouse(id,name,latitude,longitude,created_at,created_by,updated_at,updated_by, status,region_id,organization_id)" +
                                " values(?,?,?,?,?,?,?,?,?,?,?)",
                        entity.getId(),
                        entity.getName(),
                        entity.getLatitude(),
                        entity.getLongitude(),
                        entity.getCreatedAt(),
                        entity.getCreatedBy(),
                        entity.getUpdatedAt(),
                        entity.getUpdatedBy(),
                        entity.getStatus(),
                        entity.getRegionId(),
                        entity.getOrganizationId()
                );
            } else {
                saved = jdbcTemplate.update(
                        "update main.warehouse set name=?,latitude=?,longitude=?,updated_at=?,updated_by=?, status=?, region_id = ? where id=?",
                        entity.getName(),
                        entity.getLatitude(),
                        entity.getLongitude(),
                        entity.getUpdatedAt(),
                        entity.getUpdatedBy(),
                        entity.getStatus(),
                        entity.getRegionId(),
                        entity.getId());
            }

        } catch (Exception e) {
            throw new NotSavedException("Warehouse not saved");
        }

        if (saved == 1)
            return entity;
        throw new NotSavedException("Warehouse not saved");
    }

    private boolean isThere(String valId) {
        Integer val = jdbcTemplate.queryForObject("select count(*) from warehouse where not deleted and id=?", Integer.class, valId);
        if (val == null) {
            val = 0;
        }
        return val == 0;
    }

    @Override
    public Warehouse findByIdNotDeleted(String getId) {
        try {
            return jdbcTemplate.queryForObject(
                    "select o.* from warehouse o where not deleted and o.id = ?",
                    new WarehouseRowMapper(), getId);

        } catch (Exception e) {
            throw new ObjectNotFoundException("Warehouse not found", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public List<Warehouse> findAllNotDeleted(WarehouseCriteria criteria) {
        try {

            return jdbcTemplate.query("select * from warehouse where not deleted order by created_at limit ? offset ? ",
                    new WarehouseRowMapper(),
                    criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));

        } catch (Exception e) {

            throw new ObjectNotFoundException("empty", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public Long allDataCount() {
        return jdbcTemplate.queryForObject("select count(*) from warehouse where not deleted", Long.class);
    }

    @Override
    public void softDelete(String deleteId) {
        int deleted = jdbcTemplate.update(
                "update warehouse set deleted = true where id = ?",
                deleteId);
    }
}
