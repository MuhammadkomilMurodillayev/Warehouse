package com.example.warehouse.controller.user;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.auth.*;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.warehouse.controller.AbstractController.PATH;

@RestController
@RequestMapping(PATH + "/user")
public class UserController
        extends AbstractController<
        UserService,
        UserDto,
        UserCreateDto,
        UserUpdateDto,
        String,
        UserCriteria> {


    public UserController(UserService service) {
        super(service);
    }

    @Override
    @PostMapping("/")
    protected ResponseEntity<DataDto<String>> create(UserCreateDto dto) {
        return new ResponseEntity<>(new DataDto<>(service.create(dto)), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    protected void delete(@PathVariable String id) {
        service.delete(id);
    }

    @Override
    @PutMapping("/")
    protected void update(UserUpdateDto dto) {
        service.update(dto);
    }

    @Override
    @GetMapping("/{id}")
    protected ResponseEntity<DataDto<UserDto>> get(@PathVariable String id) {
        return new ResponseEntity<>(new DataDto<>(service.get(id)), HttpStatus.OK);
    }

    @Override
    @GetMapping("/getAll")
    protected ResponseEntity<DataDto<List<UserDto>>> getAll(UserCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(service.getAll(criteria)), HttpStatus.OK);
    }

}
