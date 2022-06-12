package com.example.warehouse.repository.product;

import com.example.warehouse.entity.organization.Organization;
import com.example.warehouse.entity.product.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

        Product product = new Product();

        product.setId(rs.getString("id"));
        product.setImagePath(rs.getString("imagepath"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));
        product.setDescription(rs.getString("description"));
        product.setCategoryId(rs.getString("category_id"));
        product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        product.setCreatedBy(rs.getString("created_by"));
        product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        product.setUpdatedBy(rs.getString("updated_by"));
        product.setStatus(rs.getShort("status"));
        product.setCount(rs.getInt("count"));

        return product;
    }
}
