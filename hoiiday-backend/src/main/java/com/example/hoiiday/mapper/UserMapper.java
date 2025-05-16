package com.example.hoiiday.mapper;

import com.example.hoiiday.DTO.UserDTO;
import com.example.hoiiday.model.User;

public class UserMapper {

    public static User mapToUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getUserId(),
                userDTO.getPassword(),
                userDTO.getRole(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPhoneNumber(),
                userDTO.getLastLoginAt(),
                userDTO.getLastLogoutAt()
        );
        user.setLastLoginAt(userDTO.getLastLoginAt());
        user.setLastLogoutAt(userDTO.getLastLogoutAt());
        return user;
    }

    public static UserDTO mapToUserDTO(User user) {
        UserDTO dto = new UserDTO(
                user.getUserId(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getLastLoginAt(),
                user.getLastLogoutAt()
        );
        dto.setLastLoginAt(user.getLastLoginAt());
        dto.setLastLogoutAt(user.getLastLogoutAt());
        return dto;
    }
}
