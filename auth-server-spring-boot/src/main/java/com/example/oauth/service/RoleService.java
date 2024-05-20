package com.example.oauth.service;

import com.example.oauth.entity.Role;

public interface RoleService {

    Role create(Role role);
    Role findByName(String name);

}
