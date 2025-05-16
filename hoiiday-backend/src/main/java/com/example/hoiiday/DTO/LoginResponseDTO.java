package com.example.hoiiday.DTO;

import com.example.hoiiday.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private boolean success;
    private String message;
    private UserRole role;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLoginAt;
    private LocalDateTime lastLogoutAt;
}
