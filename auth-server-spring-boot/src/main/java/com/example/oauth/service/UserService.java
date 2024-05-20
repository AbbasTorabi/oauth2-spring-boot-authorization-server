package com.example.oauth.service;

import com.example.oauth.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User create(User user);
    User findByMobile(String mobile);

}
