package com.example.warehouse.repository.user;

import com.example.warehouse.entity.auth.User;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.enums.Gender;
import com.example.warehouse.exception.ObjectNotFoundException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet resultSet, int rowNum) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();

        try {
            user.setId(resultSet.getString("id"));

            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setFirstName(resultSet.getString("firstname"));
            user.setOrganizationId(resultSet.getString("organization_id"));
            user.setLastName(resultSet.getString("lastname"));
            user.setFirstName(resultSet.getString("firstname"));
            user.setFullName(resultSet.getString("fullname"));
            user.setStatus(resultSet.getShort("status"));

            if (resultSet.getString("role") != null)
                user.setRole(Enum.valueOf(AuthRole.class, resultSet.getString("role")));

            if (resultSet.getString("gender") != null)
                user.setGender(Enum.valueOf(Gender.class, resultSet.getString("gender")));

        } catch (SQLException e) {
            throw new ObjectNotFoundException();
        }
        return user;
    }
}
