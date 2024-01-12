package ru.SVTsygankov.securityREST.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.SVTsygankov.securityREST.exceptionHandlers.NoSuchUserException;
import ru.SVTsygankov.securityREST.exceptionHandlers.UserNotUpdateException;
import ru.SVTsygankov.securityREST.model.Role;
import ru.SVTsygankov.securityREST.model.User;
import ru.SVTsygankov.securityREST.service.RoleService;
import ru.SVTsygankov.securityREST.service.UserService;
import ru.SVTsygankov.securityREST.util.UserNotCreatedException;
import ru.SVTsygankov.securityREST.util.UserValidator;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final UserValidator userValidator;

    public AdminRestController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> userList = userService.findAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

     @GetMapping("/showAccount")
    public ResponseEntity<User> showUserByUsername(Principal principal) {
           return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> allRoles() {
        List<Role> roleList = roleService.findAll();
        return ResponseEntity.ok(roleList);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<List<Role>> getRole(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId).getRoles());
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

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User updateUserFromClient,
                                           @PathVariable("id") Long id,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotUpdateException(errorMessage.toString());
        }
        userService.updateUser(updateUserFromClient, id);
        return ResponseEntity.ok(updateUserFromClient);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            throw new NoSuchUserException("Пользователя с таким id нет в бд");
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
