package com.example.warehouse.repository.product;

import com.example.warehouse.criteria.product.ProductCriteria;
import com.example.warehouse.dto.product.ProductDto;
import com.example.warehouse.entity.auth.User;
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
                        "insert into product(id,name,description,imagepath,price,created_at,created_by,updated_at,updated_by, status,category_id,count,total_price)" +
                                " values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
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
                        entity.getCategoryId(),
                        entity.getCount(),
                        entity.getTotalPrice());
            else
                jdbcTemplate.update(
                        "update product set name=?,description=?,imagepath=?,price=?,updated_at=?,updated_by=?, status=?,total_price=? where id=?",
                        entity.getName(),
                        entity.getDescription(),
                        entity.getImagePath(),
                        entity.getPrice(),
                        entity.getUpdatedAt(),
                        entity.getUpdatedBy(),
                        entity.getStatus(),
                        entity.getTotalPrice(),
                        entity.getId());

        } catch (Exception e) {
            throw new NotSavedException("Product not saved");
        }
        return entity;
    }

    @Override
    public Product findByIdNotDeleted(String findId) {
        return jdbcTemplate.queryForObject(
                "select p.* from product p where not deleted and p.id = ?",
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
            if ((criteria.getPage() == null || criteria.getSize() == null) && criteria.getCategoryId() == null)
                return jdbcTemplate.query("select * from product where not deleted order by created_at ",
                        new ProductRowMapper());

            if (criteria.getCategoryId() != null)
                return jdbcTemplate.query("select * from product where not deleted and category_id=? order by created_at",
                        new ProductRowMapper(), criteria.getCategoryId());

            return jdbcTemplate.query("select * from product where not deleted order by created_at limit ? offset ? ",
                    new ProductRowMapper(),
                    criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));

        } catch (Exception e) {

            throw new ObjectNotFoundException("empty", Arrays.toString(e.getStackTrace()));
        }
    }

    public List<ProductDto> findAllInOrganization(ProductCriteria criteria) {

        return jdbcTemplate.query("select t2.*\n" +
                        "from (select o.name, t1.*\n" +
                        "      from (select t.product_name,\n" +
                        "                   sum(t.count)       as product_count,\n" +
                        "                   sum(t.total_price) as product_total_price,\n" +
                        "                   t.price,\n" +
                        "                   t.organization_id\n" +
                        "            from (select w.organization_id, p.name as product_name, p.price, p.total_price, p.count\n" +
                            "                  from main.product_category pc\n" +
                        "                           inner join main.warehouse w on pc.warehouse_id = w.id\n" +
                        "                           inner join main.product p on pc.id = p.category_id) t\n" +
                        "            group by t.price, t.product_name, t.organization_id) t1\n" +
                        "               inner join main.organization o on t1.organization_id = o.id) t2\n" +
                        "where t2.organization_id=?",
                new ProductDtoRowMapper(), criteria.getOrganizationId());
    }

    public List<ProductDto> findAllInAllOrganization(ProductCriteria criteria) {
        return jdbcTemplate.query("select o.name, t1.* " +
                        "from (select t.product_name, " +
                        "             sum(t.count)       as product_count, " +
                        "             sum(t.total_price) as product_total_price, " +
                        "             t.price, " +
                        "             t.organization_id " +
                        "      from (select w.organization_id, p.name as product_name, p.price, p.total_price, p.count " +
                        "            from main.product_category pc " +
                        "                     inner join main.warehouse w on pc.warehouse_id = w.id " +
                        "                     inner join main.product p on pc.id = p.category_id) t "+
                        "      group by t.price, t.product_name, t.organization_id) t1 " +
                        "         inner join main.organization o on t1.organization_id = o.id;",
                new ProductDtoRowMapper());
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
