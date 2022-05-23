package com.example.warehouse.repository.user;

import com.example.warehouse.entity.auth.User;
import com.example.warehouse.exception.NotSavedException;
import com.example.warehouse.repository.BaseRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements BaseRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUserName(String username) {
        return jdbcTemplate.queryForObject(
                "select au.* from auth_user au where not deleted and au.username ilike ?",
                BeanPropertyRowMapper.newInstance(User.class),
                username);

    }


    public String save(User user) {
        int saved = jdbcTemplate.update("insert into auth_user(id,username,password,fullname,firstname,lastname,gender,organization_id,created_at,created_by,status)" +
                        " values (?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getFullName(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getGender().name(),
                        user.getOrganizationId(),
                        user.getCreatedAt(),
                        user.getCreatedBy(),
                        user.getStatus()
                }
        );

        if (saved > 0)
            return user.getId();

        throw new NotSavedException("not saved, please try again");
    }

    public User findById(String id) {

        return jdbcTemplate.queryForObject(
                "select au.* from auth_user au where not deleted and au.id = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                id);
    }
}
