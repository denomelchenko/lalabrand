package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.exception.UserAlreadyExistException;

import com.lalabrand.ecommerce.user.role.UserRole;
import com.lalabrand.ecommerce.user.role.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    // saveUser method saves a new user with valid email and password
    @Test
    public void test_saveUser_validEmailAndPassword() {
        UserRepository userRepository = mock(UserRepository.class);
        UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
        UserService userService = new UserService(userRepository, userRoleRepository);

        UserRequest userRequest = new UserRequest("password123", "test@example.com");
        UserResponse expectedResponse = new UserResponse(null, "email");
        User savedUser = new User("email", "password", 1);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse actualResponse = userService.saveUser(userRequest);

        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository).save(any(User.class));
        verify(userRoleRepository).save(any(UserRole.class));
    }

    // findByUserId method returns an optional user when a valid userId is provided
    @Test
    public void test_findByUserId_validUserId() {
        UserRepository userRepository = mock(UserRepository.class);
        UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
        UserService userService = new UserService(userRepository, userRoleRepository);

        String userId = "userId";
        User expectedUser = new User("email", "password", 1);
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.findByUserId(userId);

        assertEquals(Optional.of(expectedUser), actualUser);
        verify(userRepository).findById(userId);
    }

    // findByEmail method returns an optional user when a valid email is provided
    @Test
    public void test_findByEmail_validEmail() {
        UserRepository userRepository = mock(UserRepository.class);
        UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
        UserService userService = new UserService(userRepository, userRoleRepository);

        String email = "test@example.com";
        User expectedUser = new User("email", "password", 1);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.findByEmail(email);

        assertEquals(Optional.of(expectedUser), actualUser);
        verify(userRepository).findByEmail(email);
    }

    // updatePasswordForUser method updates the password for a user when a valid user, password and userId are provided
    @Test
    public void test_updatePasswordForUser_validUserPasswordAndUserId() throws AccessDeniedException, AccessDeniedException {
        UserRepository userRepository = mock(UserRepository.class);
        UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
        UserService userService = new UserService(userRepository, userRoleRepository);

        User expectedUser = new User("email", "password", 1);
        String password = "newPassword";
        String userId = "userId";
        User user = new User("email", "password", 1);
        when(userRepository.save(user)).thenReturn(expectedUser);

        User actualUser = userService.updatePasswordForUser(user, password, userId);

        assertEquals(expectedUser, actualUser);
        verify(userRepository).save(user);
    }

    // saveUser method throws BadCredentialsException when email or password is null
    @Test
    public void test_saveUser_nullEmailOrPassword() {
        UserRepository userRepository = mock(UserRepository.class);
        UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
        UserService userService = new UserService(userRepository, userRoleRepository);

        UserRequest userRequest = new UserRequest(null, "test@example.com");

        assertThrows(BadCredentialsException.class, () -> userService.saveUser(userRequest));
    }

    // saveUser method throws UserAlreadyExistException when a user with the same email already exists
    @Test
    public void test_saveUser_existingEmail() {
        UserRepository userRepository = mock(UserRepository.class);
        UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
        UserService userService = new UserService(userRepository, userRoleRepository);

        UserRequest userRequest = new UserRequest("password123", "test@example.com");
        User existingUser = new User("email", "password", 1);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistException.class, () -> userService.saveUser(userRequest));
    }

    // updatePasswordForUser method does not update the password when the provided user is null
    @Test
    public void test_updatePasswordForUser_nullUser() {
        UserRepository userRepository = mock(UserRepository.class);
        UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
        UserService userService = new UserService(userRepository, userRoleRepository);

        User user = null;
        String password = "newPassword";
        String userId = "userId";

        assertThrows(NullPointerException.class, () -> userService.updatePasswordForUser(user, password, userId));
    }

}
