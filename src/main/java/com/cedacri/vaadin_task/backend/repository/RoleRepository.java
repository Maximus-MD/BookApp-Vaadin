package com.cedacri.vaadin_task.backend.repository;

import com.cedacri.vaadin_task.backend.entity.Role;
import com.cedacri.vaadin_task.backend.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(UserRole name);

}
