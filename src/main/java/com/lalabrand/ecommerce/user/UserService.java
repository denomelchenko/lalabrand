package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.user.enums.Role;
import com.lalabrand.ecommerce.user.role.UserRole;
import com.lalabrand.ecommerce.user.role.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public UserResponse saveUser(UserRequest userRequest) {
        if (userRequest.email() == null || userRequest.password() == null) {
            throw new BadCredentialsException("Password or email can not be null");
        }

        User savedUser;
        User user = new User(userRequest.id(), userRequest.email(), userRequest.password());
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.password()));
        if (userRequest.id() != null) {
            Optional<User> existedUser = userRepository.findById(userRequest.id());
            if (existedUser.isPresent()) {
                user.setId(user.getId());
                user.setPassword(user.getPassword());
                user.setEmail(user.getEmail());

                savedUser = userRepository.save(existedUser.get());
            } else {
                throw new IllegalArgumentException("Can not find user with id: " + userRequest.id());
            }
        } else if (userRepository.findByEmail(userRequest.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        } else{
            userRoleRepository.save(new UserRole(Role.USER, user));
            savedUser = userRepository.save(user);
        }
        return modelMapper.map(savedUser, UserResponse.class);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
