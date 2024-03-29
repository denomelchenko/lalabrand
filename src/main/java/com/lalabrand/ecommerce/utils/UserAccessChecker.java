package com.lalabrand.ecommerce.utils;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAccessChecker {
    private final UserRepository userRepository;

    public UserAccessChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isCurrentUserEqualsId(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && SecurityContextHolder.getContext().getAuthentication().getName() != null) {
            return user.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return false;
    }
}
