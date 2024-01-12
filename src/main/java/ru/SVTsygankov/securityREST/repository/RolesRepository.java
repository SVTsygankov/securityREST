package ru.SVTsygankov.securityREST.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.SVTsygankov.securityREST.model.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
}