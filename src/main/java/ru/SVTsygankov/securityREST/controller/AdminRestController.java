package ru.SVTsygankov.securityREST.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.SVTsygankov.securityREST.model.Role;
import ru.SVTsygankov.securityREST.model.User;
import ru.SVTsygankov.securityREST.service.RoleService;
import ru.SVTsygankov.securityREST.service.UserService;
import ru.SVTsygankov.securityREST.util.UserErrorResponse;
import ru.SVTsygankov.securityREST.util.UserNotCreatedException;
import ru.SVTsygankov.securityREST.util.UserValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final UserValidator userValidator;

    public AdminRestController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> allUsers() {
        List<User> userList = userService.findAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> allRoles() {
        List<Role> roleList = roleService.findAll();
        return ResponseEntity.ok(roleList);
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                            .append(" - ").append(error.getDefaultMessage())
                            .append(";");
           }
            throw new UserNotCreatedException(errorMessage.toString());
        }
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {

        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
