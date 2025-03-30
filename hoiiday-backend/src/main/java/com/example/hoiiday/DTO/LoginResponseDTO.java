package com.example.hoiiday.DTO;

import com.example.hoiiday.model.enums.UserRole;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private boolean success;
    private UserRole role;
    private String message;
}
