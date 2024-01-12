package ru.SVTsygankov.securityREST.service;

import org.springframework.stereotype.Service;
import ru.SVTsygankov.securityREST.model.Role;
import ru.SVTsygankov.securityREST.repository.RolesRepository;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RolesRepository rolesRepository;

    public RoleServiceImp(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Role> findAll() {
        return rolesRepository.findAll();
    }
}
