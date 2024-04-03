package com.lalabrand.ecommerce.utils;

import com.lalabrand.ecommerce.security.UserDetailsImpl;
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

    public static boolean isIdInValid(String id) {
        return id == null || id.isEmpty() || id.length() > 36;
    }

    public UserDetailsImpl getCurrentUser() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
