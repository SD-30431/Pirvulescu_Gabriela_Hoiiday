package com.example.hoiiday.DTO;

import com.example.hoiiday.model.enums.UserRole;
import lombok.*;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    public UserDTO(Long userId,String password, String firstName, String lastName,
                   String email, String phoneNumber, UserRole role) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }


}
