package ru.SVTsygankov.securityREST.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.SVTsygankov.securityREST.model.User;
import ru.SVTsygankov.securityREST.service.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(userService.findByUsername(user.getEmail()) != null) {
            System.out.println("----- Пользователь с email: " + user.getEmail() + "уже существует");
            errors.rejectValue("email", "", "Пользователь с таким Email уже существует. Выберете другой");
        }
    }
}
