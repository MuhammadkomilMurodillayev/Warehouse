package com.example.warehouse.repository.user;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.criteria.organization.OrganizationCriteria;
import com.example.warehouse.entity.auth.User;
import com.example.warehouse.entity.organization.Organization;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.enums.Gender;
import com.example.warehouse.exception.BadCredentialsException;
import com.example.warehouse.exception.NotSavedException;
import com.example.warehouse.exception.ObjectNotFoundException;
import com.example.warehouse.repository.AbstractRepository;
import com.example.warehouse.repository.organization.OrganizationRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.hasRole;

@Repository
public class UserRepository implements
        AbstractRepository<
                UserCriteria,
                User,
                String> {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUserName(String username) {
        try {

            return Objects.requireNonNull(jdbcTemplate.queryForObject(
                    "select au.* from auth_user au where not au.deleted and au.username = ?",
                    (rs, rowNum) -> Optional.of(
                            new User(
                                    rs.getString("id"),
                                    rs.getTimestamp("created_at").toLocalDateTime(),
                                    rs.getString("created_by"),
                                    rs.getTimestamp("updated_at").toLocalDateTime(),
                                    rs.getString("updated_by"),
                                    rs.getBoolean("deleted"),
                                    rs.getShort("status"),
                                    rs.getString("username"),
                                    rs.getString("password"),
                                    rs.getString("firstname"),
                                    rs.getString("lastname"),
                                    rs.getString("fullname"),
                                    Gender.valueOf(rs.getString("gender")),
                                    AuthRole.valueOf(rs.getString("role")),
                                    rs.getString("organization_id"),
                                    rs.getString("phone")
                            )),
                    username)).orElse(null);

        } catch (Exception e) {
            throw new BadCredentialsException();
        }
    }

    @Override
    public User save(User user) {
        int saved;
        try {
            if (isThere(user.getId()))
                saved = jdbcTemplate.update("insert into auth_user(id,username,password,fullname,firstname,lastname,gender,organization_id,created_at,created_by,updated_at,updated_by,status,role,phone)" +
                                " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getGender().name(),
                        user.getOrganizationId(),
                        user.getCreatedAt(),
                        user.getCreatedBy(),
                        user.getUpdatedAt(),
                        user.getUpdatedBy(),
                        user.getStatus(),
                        user.getRole().name(),
                        user.getPhone());
            else
                saved = jdbcTemplate.update(
                        "update auth_user set username=?,password=?,firstname=?,lastname=?,fullname=?,gender=?,organization_id=?,updated_at=?,updated_by=?, status=? where id=?",
                        user.getUsername(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getGender().name(),
                        user.getOrganizationId(),
                        user.getUpdatedBy(),
                        user.getUpdatedAt(),
                        user.getStatus(),
                        user.getId());
        } catch (Exception e) {
            throw new NotSavedException("Not saved, please try again", Arrays.toString(e.getStackTrace()));
        }
        if (saved == 1)
            return user;
        else throw new NotSavedException("Not saved, please try again");
    }

    @Override
    public User findByIdNotDeleted(String id) {
        return jdbcTemplate.queryForObject(
                "select au.* from auth_user au where not deleted and au.id = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                id);
    }

    @Override
    public List<User> findAll(UserCriteria criteria) {
        try {
            if (criteria.getWarehouseId() != null) {
                return jdbcTemplate.query("select * from auth_user_warehouse aw inner join main.auth_user au on au.id = aw.auth_user_id " +
                                "inner join warehouse w on aw.warehouse_id = w.id where aw.warehouse_id=? order by au.created_at limit ? offset ? ",
                        new UserRowMapper(),
                        criteria.getWarehouseId(), criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));

            } else if (criteria.getOrganizationId() != null) {
                return jdbcTemplate.query("select * from auth_user where organization_id = ? order by created_at limit ? offset ? ",
                        new UserRowMapper(),
                        criteria.getOrganizationId(), criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));

            } else {
                return jdbcTemplate.query("select * from auth_user order by created_at limit ? offset ? ",
                        new UserRowMapper(),
                        criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));
            }

        } catch (Exception e) {

            throw new ObjectNotFoundException("empty", Arrays.toString(e.getStackTrace()));
        }
    }

    public boolean isThere(String valId) {
        Integer val = jdbcTemplate.queryForObject("select count(*) from auth_user where not deleted and id=?", Integer.class, valId);
        if (val == null) {
            val = 0;
        }
        return val == 0;
    }

    public void setStatus(short newStatus, String newId) {
        jdbcTemplate.update("update auth_user set status=? where id=?",
                newStatus, newId);
    }

    @Override
    public Long allDataCount() {
        return jdbcTemplate.queryForObject("select count(*) from auth_user where not deleted", Long.class);
    }

    @Override
    public void softDelete(String deleteId) {
        int deleted = jdbcTemplate.update(
                "update auth_user set deleted = true where id = ?",
                deleteId);
    }

}

