package com.example.oauth.repository;

import com.example.oauth.entity.GrantType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantTypeRepository extends JpaRepository<GrantType, String> {

    GrantType findByName(String name);

}
