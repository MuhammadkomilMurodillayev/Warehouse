package com.example.warehouse.repository.user;

import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.entity.auth.User;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.enums.Gender;
import com.example.warehouse.exception.BadCredentialsException;
import com.example.warehouse.exception.NotSavedException;
import com.example.warehouse.exception.ObjectNotFoundException;
import com.example.warehouse.repository.AbstractRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

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
        try {
            if (isThere(user.getId())) {
                jdbcTemplate.update("insert into auth_user(id,username,password,fullname,firstname,lastname,gender,organization_id,created_at,created_by,updated_at,updated_by,status,role,phone)" +
                                " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        user.getId(),
                        user.getUsername().toLowerCase(),
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


            } else
                jdbcTemplate.update(
                        "update main.auth_user set username=?, firstname=?, lastname=?, fullname=?, gender=?, organization_id=?,  updated_by=?,updated_at=?, status=?, image_path=?,role=? where id=?",
                        user.getUsername().toLowerCase(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getFullName(),
                        user.getGender().name(),
                        user.getOrganizationId(),
                        user.getUpdatedBy(),
                        user.getUpdatedAt(),
                        user.getStatus(),
                        user.getImagePath(),
                        user.getRole().name(),
                        user.getId());
        } catch (Exception e) {
            throw new NotSavedException("Not saved, please try again", Arrays.toString(e.getStackTrace()));
        }
        return user;
    }

    @Override
    public User findByIdNotDeleted(String id) {
        return jdbcTemplate.queryForObject(
                "select au.* from auth_user au where not deleted and au.id = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                id);
    }

    @Override
    public List<User> findAllNotDeleted(UserCriteria criteria) {
        try {
            if (criteria.getWarehouseId() != null) {
                return jdbcTemplate.query("select * from auth_user_warehouse aw inner join main.auth_user au on au.id = aw.auth_user_id " +
                                "inner join warehouse w on aw.warehouse_id = w.id where not au.deleted and aw.warehouse_id=? order by au.created_at desc limit ? offset ? ",
                        new UserRowMapper(),
                        criteria.getWarehouseId(), criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));

            } else if (criteria.getPage() == null && criteria.getSize() == null && criteria.getOrganizationId() != null) {
                return jdbcTemplate.query("select * from auth_user where not deleted and organization_id = ? order by created_at desc ",
                        new UserRowMapper(),
                        criteria.getOrganizationId());

            } else if (criteria.getPage() != null && criteria.getSize() != null && criteria.getOrganizationId() != null) {
                return jdbcTemplate.query("select * from auth_user where not deleted and organization_id = ? order by created_at desc limit ? offset ? ",
                        new UserRowMapper(),
                        criteria.getOrganizationId(), criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));

            } else if (criteria.getSize() != null && criteria.getPage() != null && hasRole(AuthRole.SUPER_ADMIN)) {
                return jdbcTemplate.query("select * from auth_user  where not deleted  order by created_at desc limit ? offset ? ",
                        new UserRowMapper(),
                        criteria.getSize(), (criteria.getSize() * (criteria.getPage() - 1)));

            } else if (hasRole(AuthRole.SUPER_ADMIN)) {
                return jdbcTemplate.query("select au.* " +
                                "from main.auth_user au " +
                                "         inner join main.organization o on o.id = au.organization_id " +
                                "where not au.deleted " +
                                "  and not o.deleted " +
                                "order by au.created_at desc",
                        new UserRowMapper());

            } else return null;

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

    public boolean usernameUnique(String username) {
        Integer val = jdbcTemplate.queryForObject("select count(au.username) from auth_user au where au.username ilike ?", Integer.class, username);
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

    public void saveRelationUserAndWarehouse(String warehouseId, String employeeId) {
        if (isThereEmployee(employeeId))
            jdbcTemplate.update("insert into auth_user_warehouse(auth_user_id,warehouse_id) values(?,?)",
                    employeeId,
                    warehouseId);
        else jdbcTemplate.update("update auth_user_warehouse set warehouse_id = ? where auth_user_id=?",
                warehouseId,
                employeeId);
    }

    public boolean isThereEmployee(String valId) {
        Integer val = jdbcTemplate.queryForObject("select count(*) from auth_user_warehouse where auth_user_id=?", Integer.class, valId);
        if (val == null) {
            val = 0;
        }
        return val == 0;
    }
}

