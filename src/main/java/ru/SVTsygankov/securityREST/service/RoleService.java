package ru.SVTsygankov.securityREST.service;

import ru.SVTsygankov.securityREST.model.Role;
import java.util.List;

public interface RoleService {
    List<Role> findAll();
}