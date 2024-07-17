package com.example.oauth.repository;

import com.example.oauth.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScopeRepository extends JpaRepository<Scope, String> {

    Scope findByName(String name);

}
