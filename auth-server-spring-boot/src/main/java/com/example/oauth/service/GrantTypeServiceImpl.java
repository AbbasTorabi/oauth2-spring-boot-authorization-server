package com.example.oauth.service;

import com.example.oauth.entity.GrantType;
import com.example.oauth.entity.Role;
import com.example.oauth.repository.GrantTypeRepository;
import com.example.oauth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrantTypeServiceImpl implements GrantTypeService {

    private final GrantTypeRepository repository;

    @Override
    public GrantType create(GrantType grantType) {
        grantType.assignId();
        GrantType createdGrantType = repository.save(grantType);
        return createdGrantType;
    }

    @Override
    public GrantType findByName(String name) {
        return repository.findByName(name);
    }
}
