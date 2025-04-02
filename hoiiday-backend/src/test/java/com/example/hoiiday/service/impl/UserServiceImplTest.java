package com.example.hoiiday.service.impl;

import com.example.hoiiday.DTO.UserDTO;
import com.example.hoiiday.mapper.UserMapper;
import com.example.hoiiday.model.Admin;
import com.example.hoiiday.model.User;
import com.example.hoiiday.model.enums.UserRole;
import com.example.hoiiday.repository.AdminRepository;
import com.example.hoiiday.repository.UserRepository;
import com.example.hoiiday.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest{

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPhoneNumber("123-456-7890");
        userDTO.setRole(UserRole.CLIENT);
        userDTO.setPassword("password");

        user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("123-456-7890");
        user.setRole(UserRole.CLIENT);
        user.setPassword("encodedPassword");
    }

    @Test
    void createUser_shouldReturnUserDTO() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getFirstName(), result.getFirstName());
        assertEquals(userDTO.getLastName(), result.getLastName());
    }

    @Test
    void getAllUsers_shouldReturnSetOfUserDTOs() {
        Set<User> users = new HashSet<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users.stream().collect(Collectors.toList()));

        Set<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDTO.getFirstName(), result.iterator().next().getFirstName());
    }

    @Test
    void getUserById_shouldReturnUserDTO() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(userDTO.getFirstName(), result.getFirstName());
    }

    @Test
    void updateUser_shouldReturnUpdatedUserDTO() {
        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setFirstName("Jane");
        updatedUserDTO.setLastName("Doe");
        updatedUserDTO.setEmail("jane.doe@example.com");
        updatedUserDTO.setPhoneNumber("987-654-3210");
        // updatedUserDTO.setRole(UserRole.CLIENT);

        User updatedUser = new User(); // Create a new User for update
        updatedUser.setUserId(1L);
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setEmail("jane.doe@example.com");
        updatedUser.setPhoneNumber("987-654-3210");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDTO result = userService.updateUser(1L, updatedUserDTO);

        assertNotNull(result);
        assertEquals(updatedUserDTO.getFirstName(), result.getFirstName());
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void findUserByEmail_shouldReturnUser() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        User foundUser = userService.findUserByEmail("john.doe@example.com");

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    void findUserByEmail_shouldReturnNullIfNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        User foundUser = userService.findUserByEmail("nonexistent@example.com");

        assertNull(foundUser);
    }

    @Test
    void getUserById_shouldThrowExceptionIfUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }

    @Test
    void updateUser_shouldThrowExceptionIfUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, userDTO));
    }
}