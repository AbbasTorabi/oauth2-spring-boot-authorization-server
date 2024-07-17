package com.example.oauth.service;

import com.example.oauth.entity.Role;
import com.example.oauth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role create(Role role) {
        role.assignId();
        Role createdRole = repository.save(role);
        return createdRole;
    }

    @Override
    public Role findByName(String name) {
        return repository.findByName(name);
    }
}
