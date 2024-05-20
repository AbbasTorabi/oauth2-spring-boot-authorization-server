package com.example.oauth.repository;

import com.example.oauth.entity.Role;
import com.example.oauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByName(String name);

}
