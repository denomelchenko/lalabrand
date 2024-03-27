package com.lalabrand.ecommerce.utils;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAccessChecker {
    private final UserService userService;

    public UserAccessChecker(UserService userService) {
        this.userService = userService;
    }

    public boolean isCurrentUserEqualsId(Integer id) {
        Optional<User> user = userService.findByUserId(id);
        if (user.isPresent()) {
            return user.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return false;
    }
}
