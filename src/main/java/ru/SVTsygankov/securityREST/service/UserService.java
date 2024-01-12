package ru.SVTsygankov.securityREST.service;

import ru.SVTsygankov.securityREST.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findUserById(Long id);

    User findByUsername(String email);

    boolean deleteUser(Long id);

    boolean saveUser(User user);

    void updateUser(User user, Long id);
}
