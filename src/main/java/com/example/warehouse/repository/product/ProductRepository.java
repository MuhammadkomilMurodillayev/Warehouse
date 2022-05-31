package com.example.warehouse.repository.product;

import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.entity.product.Product;
import com.example.warehouse.entity.product.ProductCategory;
import com.example.warehouse.exception.NotSavedException;
import com.example.warehouse.exception.ObjectNotFoundException;
import com.example.warehouse.repository.AbstractRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ProductRepository implements AbstractRepository<
        ProductCriteria,
        Product,
        String> {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product save(Product entity) {
        try {
            if (isThere(entity.getId()))
                jdbcTemplate.update(
                        "insert into product(id,name,description,imagepath,price,created_at,created_by,updated_at,updated_by, status,catagory_id)" +
                                " values(?,?,?,?,?,?,?,?,?,?,?)",
                        entity.getId(),
                        entity.getName(),
                        entity.getDescription(),
                        entity.getImagePath(),
                        entity.getPrice(),
                        entity.getCreatedAt(),
                        entity.getCreatedBy(),
                        entity.getUpdatedAt(),
                        entity.getUpdatedBy(),
                        entity.getStatus(),
                        entity.getCategoryId()
                );
            else
                jdbcTemplate.update(
                        "update product set name=?,description=?,imagepath=?,price=?,updated_at=?,updated_by=?, status=? where id=?",
                        entity.getName(),
                        entity.getDescription(),
                        entity.getImagePath(),
                        entity.getPrice(),
                        entity.getUpdatedAt(),
                        entity.getUpdatedBy(),
                        entity.getStatus(),
                        entity.getId());

        } catch (Exception e) {
            throw new NotSavedException("Product not saved");
        }
        return entity;
    }

    @Override
    public Product findByIdNotDeleted(String findId) {
        return jdbcTemplate.queryForObject(
                "select p.* from product p where not deleted and pc.id = ?",
                BeanPropertyRowMapper.newInstance(Product.class),
                findId);
    }

    private boolean isThere(String valId) {
        Integer val = jdbcTemplate.queryForObject("select count(*) from product where not deleted and id=?", Integer.class, valId);
        if (val == null) {
            val = 0;
        }
        return val == 0;
    }

    @Override
    public List<Product> findAllNotDeleted(ProductCriteria criteria) {
        try {

            return jdbcTemplate.query("select * from product where not deleted order by created_at limit ? offset ? ",
                    new ProductRowMapper(),
                    criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));

        } catch (Exception e) {

            throw new ObjectNotFoundException("empty", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public Long allDataCount() {
        return jdbcTemplate.queryForObject("select count(*) from product where not deleted", Long.class);
    }

    @Override
    public void softDelete(String deleteId) {
        int deleted = jdbcTemplate.update(
                "update product set deleted = true where id = ?",
                deleteId);
    }
}
