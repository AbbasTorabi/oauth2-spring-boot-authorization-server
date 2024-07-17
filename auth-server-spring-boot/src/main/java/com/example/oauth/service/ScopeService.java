package com.example.oauth.service;

import com.example.oauth.entity.Scope;

public interface ScopeService {

    Scope create(Scope scope);
    Scope findByName(String name);

}
