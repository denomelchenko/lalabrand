package com.lalabrand.ecommerce.utils;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAccessChecker {
    private final UserRepository userRepository;

    public UserAccessChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isCurrentUserOwnerOfId(String id) {
        if (id != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Optional<User> user = userRepository.findById(id);
                if (user.isPresent()) {
                    return user.get().getEmail().equals(authentication.getName());
                }
            }
        }
        return false;

    }
}
