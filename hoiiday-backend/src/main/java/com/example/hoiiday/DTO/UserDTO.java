package com.example.hoiiday.DTO;

import com.example.hoiiday.model.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime lastLoginAt;
    private LocalDateTime lastLogoutAt;

    public UserDTO(Long userId,String password, String firstName, String lastName,
                   String email, String phoneNumber, UserRole role ,  LocalDateTime lastLoginAt,LocalDateTime lastLogoutAt)
 {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.lastLoginAt = lastLoginAt;
        this.lastLogoutAt = lastLogoutAt;
    }


}
