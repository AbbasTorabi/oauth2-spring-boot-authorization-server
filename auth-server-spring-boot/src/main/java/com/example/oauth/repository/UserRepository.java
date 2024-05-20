package com.example.oauth.repository;

import com.example.oauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User getUserByMobile(String mobile);

}
