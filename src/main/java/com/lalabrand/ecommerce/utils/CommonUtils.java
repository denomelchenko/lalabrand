package com.lalabrand.ecommerce.utils;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommonUtils {
    private final UserService userService;

    public CommonUtils(UserService userService) {
        this.userService = userService;
    }

    public static boolean isIdValid(String id) {
        return id.isEmpty() || id.length() > 36;
    }

    public Optional<User> getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!email.isEmpty()) {
            return userService.findByEmail(email);
        }
        return Optional.empty();
    }
}
