package com.example.hoiiday.mapper;

import com.example.hoiiday.DTO.UserDTO;
import com.example.hoiiday.model.User;

public class UserMapper {

    public static User mapToUser(UserDTO userDTO) {
        return new User(
                userDTO.getUserId(),
                userDTO.getPassword(),
                userDTO.getRole(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPhoneNumber()
        );
    }

    public static UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

}
