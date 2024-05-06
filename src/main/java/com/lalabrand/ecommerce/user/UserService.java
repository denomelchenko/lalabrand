package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.exception.UserAlreadyExistException;
import com.lalabrand.ecommerce.user.enums.Role;
import com.lalabrand.ecommerce.user.role.UserRole;
import com.lalabrand.ecommerce.user.role.UserRoleRepository;
import com.lalabrand.ecommerce.utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
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
            throw new UserAlreadyExistException("User with this email already exist");
        } else {
            userRoleRepository.save(new UserRole(Role.USER, user));
            savedUser = userRepository.save(user);
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
        return UserDTO.fromEntity(userRepository.save(
                updateUserFields(userRepository.findById(CommonUtils.getCurrentUser().getId()).orElseThrow(() ->
                        new UsernameNotFoundException("Current user does not exist")
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
}
