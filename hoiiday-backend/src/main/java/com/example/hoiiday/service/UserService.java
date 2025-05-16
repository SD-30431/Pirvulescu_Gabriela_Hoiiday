package com.example.hoiiday.service;

import com.example.hoiiday.DTO.UserDTO;
import com.example.hoiiday.model.User;

import java.util.Set;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    Set<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    User findUserByEmail(String email);
    User save(User user);
    User getUserEntityById(Long userId);

}
