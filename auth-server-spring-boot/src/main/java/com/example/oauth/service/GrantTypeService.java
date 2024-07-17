package com.example.oauth.service;

import com.example.oauth.entity.GrantType;

public interface GrantTypeService {

    GrantType create(GrantType grantType);
    GrantType findByName(String name);

}
