package com.example.hoiiday.service;

import com.example.hoiiday.DTO.UserDTO;

import java.util.Set;

public interface ClientService {
    Set<UserDTO> getAllClients();
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
}
