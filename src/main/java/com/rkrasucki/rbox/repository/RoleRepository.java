package com.rkrasucki.rbox.repository;

import com.rkrasucki.rbox.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(String role);
}
