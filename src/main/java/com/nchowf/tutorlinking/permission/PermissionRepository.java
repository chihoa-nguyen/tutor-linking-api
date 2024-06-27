package com.nchowf.tutorlinking.permission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {

    Boolean existsByName(String name);
}
