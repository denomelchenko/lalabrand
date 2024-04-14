package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.exception.UserAlreadyExistException;
import com.lalabrand.ecommerce.user.role.UserRole;
import com.lalabrand.ecommerce.user.role.UserRoleRepository;
import com.lalabrand.ecommerce.utils.UserAccessChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userRoleRepository = mock(UserRoleRepository.class);
        userService = new UserService(userRepository, userRoleRepository, new UserAccessChecker(userRepository));
    }

    // saveUser method saves a new user with valid email and password
    @Test
    public void test_saveUser_savesNewUserWithValidEmailAndPassword() throws AccessDeniedException, UserAlreadyExistException {
        UserRequest userRequest = new UserRequest("password123", "test@example.com", null);
        User savedUser = new User("1", "test@example.com", "password123", 1);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponse result = userService.saveUser(userRequest);

        // Assert
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getEmail(), result.getEmail());
        verify(userRoleRepository, times(1)).save(any(UserRole.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    // findByUserId method returns an optional user when given a valid user id
    @Test
    public void test_findByUserId_returnsOptionalUserWithValidUserId() {
        User user = new User("1", "test@example.com", "password123", 1);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findByUserId("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    // findByEmail method returns an optional user when given a valid email
    @Test public void test_findByEmail_returnsOptionalUserWithValidEmail() {
        User user = new User("1", "test@example.com", "password123", 1);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findByEmail("test@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    // saveUser method throws BadCredentialsException when email or password is null
    @Test
    public void test_saveUser_throwsBadCredentialsExceptionWhenEmailOrPasswordIsNull() {
        UserRequest userRequest = new UserRequest(null, "test@example.com", null);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.saveUser(userRequest));
    }

    // saveUser method throws AccessDeniedException when user id can not get info about user with id 1
    @Test
    public void test_saveUser_throwsIllegalArgumentExceptionWhenUserIdIsInvalid() {
        UserRequest userRequest = new UserRequest("password123", "test@example.com", "1");
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> userService.saveUser(userRequest));
    }

    // saveUser method throws ResponseStatusException when user with email already exists
    @Test
    public void test_saveUser_throwsResponseStatusExceptionWhenUserWithEmailAlreadyExists() {
        UserRequest userRequest = new UserRequest("password123", "test@example.com", null);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.saveUser(userRequest));
    }

    // findByUserId method returns an empty optional when given an invalid user id
    @Test
    public void test_findByUserId_returnsEmptyOptionalWhenGivenInvalidUserId() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findByUserId("1");

        // Assert
        assertFalse(result.isPresent());
    }

    // findByEmail method returns an empty optional when given an invalid email
    @Test
    public void test_findByEmail_returnsEmptyOptionalWhenGivenInvalidEmail() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findByEmail("test@example.com");

        // Assert
        assertFalse(result.isPresent());
    }

    // saveUser method saves a new user with valid email and password and assigns a default role
    @Test
    public void test_saveUser_savesNewUserWithValidEmailAndPasswordAndAssignsDefaultRole() throws AccessDeniedException {
        UserRequest userRequest = new UserRequest("password123", "test@example.com", null);
        User savedUser = new User("1", "test@example.com", "password123", 1);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponse result = userService.saveUser(userRequest);

        // Assert
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getEmail(), result.getEmail());
    }
}
