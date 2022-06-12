package com.example.warehouse.repository.product;

import com.example.warehouse.dto.product.ProductDto;
import com.example.warehouse.entity.product.Product;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDtoRowMapper implements RowMapper<ProductDto> {
    @Override
    public ProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        ProductDto product = new ProductDto();

        product.setOrganizationName(rs.getString("name"));
        product.setName(rs.getString("product_name"));
        product.setCount(rs.getInt("product_count"));
        product.setTotalPrice(rs.getDouble("product_total_price"));
        product.setPrice(rs.getDouble("price"));
        product.setOrganizationId(rs.getString("organization_id"));


        return product;
    }
}