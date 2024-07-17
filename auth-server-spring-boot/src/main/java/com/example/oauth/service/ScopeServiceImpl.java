package com.example.oauth.service;

import com.example.oauth.entity.Scope;
import com.example.oauth.repository.ScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScopeServiceImpl implements ScopeService {

    private final ScopeRepository repository;

    @Override
    public Scope create(Scope scope) {
        scope.assignId();
        Scope createdScope = repository.save(scope);
        return createdScope;
    }

    @Override
    public Scope findByName(String name) {
        return repository.findByName(name);
    }
}
