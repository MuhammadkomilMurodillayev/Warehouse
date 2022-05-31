package com.example.warehouse.repository.product;

import com.example.warehouse.criteria.product.ProductCategoryCriteria;
import com.example.warehouse.entity.product.ProductCategory;
import com.example.warehouse.exception.AlreadyExitsException;
import com.example.warehouse.exception.NotSavedException;
import com.example.warehouse.exception.ObjectNotFoundException;
import com.example.warehouse.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductCategoryRepository implements AbstractRepository<
        ProductCategoryCriteria,
        ProductCategory,
        String> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProductCategory save(ProductCategory entity) {
        try {
            if (isThere(entity.getId()))
                jdbcTemplate.update(
                        "insert into product_category(id,name,code,created_at,created_by,updated_at,updated_by, status,organization_id)" +
                                " values(?,?,?,?,?,?,?,?,?)",
                        entity.getId(),
                        entity.getName(),
                        entity.getName().toUpperCase().replace(" ", "_"),
                        entity.getCreatedAt(),
                        entity.getCreatedBy(),
                        entity.getUpdatedAt(),
                        entity.getUpdatedBy(),
                        entity.getStatus(),
                        entity.getOrganizationId()
                );
            else
                jdbcTemplate.update(
                        "update product_category set name=?,code=?,updated_at=?,updated_by=? where id=?",
                        entity.getName(),
                        entity.getName().toUpperCase().replace(" ", "_"),
                        entity.getUpdatedAt(),
                        entity.getUpdatedBy(),
                        entity.getId());

        } catch (Exception e) {
            throw new NotSavedException("ProductCategory not saved");
        }
        return entity;
    }

    private boolean isThere(String valId) {
        Integer val = jdbcTemplate.queryForObject("select count(*) from product_category where not deleted and id=?", Integer.class, valId);
        if (val == null) {
            val = 0;
        }
        return val == 0;
    }

    @Override
    public List<ProductCategory> findAllNotDeleted(ProductCategoryCriteria criteria) {
        try {
            return jdbcTemplate.query("select * from product_category where not deleted and organization_id=? order by created_at",
                    (res, rowNum) -> Optional.of(
                            new ProductCategory(
                                    res.getString("id"),
                                    res.getShort("status"),
                                    res.getString("name"),
                                    res.getString("code"),
                                    res.getString("organization_id")
                            )).orElse(null), criteria.getOrganizationId());

        } catch (Exception e) {

            throw new ObjectNotFoundException("empty", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public ProductCategory findByIdNotDeleted(String findId) {
        return jdbcTemplate.queryForObject(
                "select pc.* from product_category pc where not deleted and pc.id = ?",
                (res, row) -> Optional.of(
                        new ProductCategory(
                                res.getString("id"),
                                res.getString("name"),
                                res.getString("organization_id")
                        )
                ).orElse(null),
                findId);
    }

    @Override
    public Long allDataCount() {
        return jdbcTemplate.queryForObject("select count(*) from product_category where not deleted", Long.class);
    }

    @Override
    public void softDelete(String deleteId) {
        int deleted = jdbcTemplate.update(
                "update product set deleted = true where id = ?",
                deleteId);
    }

    public void checkCategoryName(String name, String organizationId) {

        Integer count = jdbcTemplate.queryForObject("select count(*) from product_category pc where pc.name=? and pc.organization_id=?", Integer.class, name, organizationId);
        if (!(count == null || count == 0))
            throw new AlreadyExitsException("this category already exist");

    }

}
