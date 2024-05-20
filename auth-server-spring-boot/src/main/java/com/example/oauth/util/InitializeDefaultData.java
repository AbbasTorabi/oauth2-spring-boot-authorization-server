package com.example.oauth.util;

import com.example.oauth.entity.Role;
import com.example.oauth.entity.User;
import com.example.oauth.service.RoleService;
import com.example.oauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitializeDefaultData implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role superAdmin;
        Role contentAdmin;

        superAdmin = roleService.findByName("ROLE_SUPER_ADMIN");

        if (superAdmin == null) {

            superAdmin = roleService.create(
                    Role.builder()
                            .name("ROLE_SUPER_ADMIN")
                            .build()
            );

            roleService.create(superAdmin);
        }

        contentAdmin = roleService.findByName("ROLE_CONTENT_ADMIN");

        if (contentAdmin == null) {

            contentAdmin = roleService.create(
                    Role.builder()
                            .name("ROLE_CONTENT_ADMIN")
                            .build()
            );

            roleService.create(contentAdmin);
        }

        User abbas = userService.findByMobile("091212345");

        if (abbas == null) {

            User user = userService.create(
                    User.builder()
                            .name("Abbas")
                            .mobile("091212345")
                            .password("123456")
                            .roles(Set.of(superAdmin, contentAdmin))
                            .build()
            );

            userService.create(user);
        }

    }

}
