package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.exception.UserAlreadyExistException;
import com.lalabrand.ecommerce.user.enums.Role;
import com.lalabrand.ecommerce.user.role.UserRole;
import com.lalabrand.ecommerce.user.role.UserRoleRepository;
import com.lalabrand.ecommerce.utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public UserDTO saveUser(UserRequest userRequest) {
        if (userRequest.getEmail() == null || userRequest.getPassword() == null) {
            throw new BadCredentialsException("Password or email can not be null");
        }

        User savedUser;
        User user = new User(userRequest.getEmail(), userRequest.getPassword(), 1);
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            logger.error("User with email {} already exists", userRequest.getEmail());
            throw new UserAlreadyExistException("User with this email already exist");
        } else {
            user.setBonus(0);
            userRoleRepository.save(new UserRole(Role.USER, user));
            savedUser = userRepository.save(user);
            logger.info("User with email {} created successfully", savedUser.getEmail());
        }
        return UserDTO.fromEntity(savedUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User updatePasswordForUser(User user, String password, String userId) {
        user.setPassword(password);
        user.setPasswordVersion(user.getPasswordVersion() + 1);
        user.setId(userId);
        return userRepository.save(user);
    }

    @Transactional
    public UserDTO updateUser(UserUpdateRequest userUpdateRequest) {
        String currentUserId = CommonUtils.getCurrentUserId();
        return UserDTO.fromEntity(userRepository.save(
                updateUserFields(userRepository.findById(currentUserId).orElseThrow(() -> {
                            logger.error("User with id {} does not exists", currentUserId);
                            return new UsernameNotFoundException("Error, the current user has been deleted");
                        }
                ), userUpdateRequest)));
    }

    private User updateUserFields(User user, UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest.getFirstName() != null) {
            user.setFirstName(userUpdateRequest.getFirstName());
        }
        if (userUpdateRequest.getLastName() != null) {
            user.setLastName(userUpdateRequest.getLastName());
        }
        if (userUpdateRequest.getPhone() != null) {
            user.setPhone(userUpdateRequest.getPhone());
        }
        if (userUpdateRequest.getLanguage() != null) {
            user.setLanguage(userUpdateRequest.getLanguage());
        }
        return user;
    }

    public UserDTO getUserInfoById(String id) {
        return UserDTO.fromEntity(userRepository.findById(id).orElseThrow(() -> {
            logger.error("User with id {} does not exists", id);
            return new UsernameNotFoundException("Error, the current user has been deleted");
        }));
    }
}
