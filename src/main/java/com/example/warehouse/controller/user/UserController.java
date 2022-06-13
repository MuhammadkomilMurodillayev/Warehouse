package com.example.warehouse.controller.user;

import com.example.warehouse.controller.AbstractController;
import com.example.warehouse.criteria.auth.UserCriteria;
import com.example.warehouse.dto.auth.*;
import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.enums.AuthRole;
import com.example.warehouse.enums.Gender;
import com.example.warehouse.repository.user.UserRepository;
import com.example.warehouse.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.example.warehouse.config.security.utils.UtilsForSessionUser.getSessionUser;
import static com.example.warehouse.config.security.utils.UtilsForSessionUser.hasRole;
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

    private final UserRepository repository;

    public UserController(UserService service, UserRepository repository) {
        super(service);
        this.repository = repository;
    }

    @Override
    @Operation(summary = "create user")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','ADMIN','MANAGER')")
    @PostMapping("/create")
    protected ResponseEntity<DataDto<String>> create(@RequestBody UserCreateDto dto) {

        return new ResponseEntity<>(new DataDto<>(service.create(dto)), HttpStatus.OK);
    }

    @Override
    @Operation(summary = "delete user")
    @DeleteMapping("/delete/{id}")
    protected ResponseEntity<DataDto<String>> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(new DataDto<>("deleted"), HttpStatus.OK);
    }

    @Override
    @Operation(summary = "update user")
    @PutMapping("/update/{id}")
    protected ResponseEntity<DataDto<String>> update(@RequestBody UserUpdateDto dto, @PathVariable String id) {
        dto.setId(id);
        service.update(dto);
        return new ResponseEntity<>(new DataDto<>("updated"), HttpStatus.OK);
    }

    @Operation(summary = "update session user")
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    protected ResponseEntity<String> update(@RequestParam String firstName,
                                            @RequestParam String lastName,
                                            @RequestParam String gender,
                                            @RequestParam String phone,
                                            @RequestParam MultipartFile image) {
        UserProfileUpdateDto dto = UserProfileUpdateDto.builder()
                .lastName(lastName)
                .firstName(firstName)
                .gender(Gender.valueOf(gender.toUpperCase()))
                .image(image)
                .phone(phone)
                .build();
        service.update(dto);
        return new ResponseEntity<>("updated", HttpStatus.OK);
    }

    @Override
    @Operation(summary = "get user")
    @GetMapping("/get/{id}")
    protected ResponseEntity<DataDto<UserDto>> get(@PathVariable String id) {
        return new ResponseEntity<>(new DataDto<>(service.get(id)), HttpStatus.OK);
    }

    @Operation(summary = "get session user")
    @GetMapping("/get")
    protected ResponseEntity<DataDto<UserDto>> get() {
        return get(getSessionUser().getId());
    }

    @Operation(summary = "reset password session user")
    @PutMapping("/resetPassword")
    protected ResponseEntity<String> resetPassword(@RequestBody UserResetPasswordDto dto) {

        service.resetPassword(dto);

        return new ResponseEntity<>("updated", HttpStatus.OK);

    }

    @Override
    @Operation(summary = "get all Users")
    @GetMapping("/getAll")
    protected ResponseEntity<DataDto<List<UserDto>>> getAll(UserCriteria criteria) {
        List<UserDto> userList = service.getAll(criteria);
        Integer totalData = userList.size();
        Long allData = repository.allDataCount();
        return new ResponseEntity<>(new DataDto<>(userList, totalData, allData), HttpStatus.OK);
    }

    @Operation(summary = "get all roles")
    @GetMapping("/getAllRole")
    protected ResponseEntity<DataDto<List<String>>> getAll() {

        if (hasRole(AuthRole.SUPER_ADMIN))
            return new ResponseEntity<>(new DataDto<>(List.of("change role", "ADMIN")), HttpStatus.OK);
        else if (hasRole(AuthRole.ADMIN))
            return new ResponseEntity<>(new DataDto<>(List.of("change role", "EMPLOYEE", "MANAGER")), HttpStatus.OK);
        else if (hasRole(AuthRole.MANAGER))
            return new ResponseEntity<>(new DataDto<>(List.of("change role", "EMPLOYEE")), HttpStatus.OK);
        else return null;
    }
}
