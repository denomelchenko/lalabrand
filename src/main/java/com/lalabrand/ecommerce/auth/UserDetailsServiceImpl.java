package com.lalabrand.ecommerce.auth;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("LoadUserByUsername started...");

        Optional<User> user = userService.findByEmail(username);
        if (user.isEmpty()) {
            logger.error("Username with email " + username + " not found");
            throw new UsernameNotFoundException("Could not found user:" + username);
        }
        logger.info("User Authenticated successfully");
        return new UserDetailsImpl(user.get());
    }
}
