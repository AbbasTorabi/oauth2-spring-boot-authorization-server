package com.example.oauth.service;

import com.example.oauth.entity.User;
import com.example.oauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.getUserByMobile(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getMobile(), user.getPassword(), true, true, true, true, user.getGrantedAuthorities()
        );
    }

    @Override
    public User create(User user) {
        user.assignId();
        User createdUser = repository.save(user);
        return createdUser;
    }

    @Override
    public User findByMobile(String mobile) {
        return repository.getUserByMobile(mobile);
    }
}
