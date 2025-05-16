package com.example.hoiiday.controller;

import com.example.hoiiday.DTO.*;
import com.example.hoiiday.mapper.UserMapper;
import com.example.hoiiday.model.User;
import com.example.hoiiday.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = new LoginResponseDTO();
        User user = userService.findUserByEmail(request.getEmail());

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            user.setLastLoginAt(LocalDateTime.now());
            userService.save(user);

            response.setSuccess(true);
            response.setRole(user.getRole());
            response.setMessage("Login successful");
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setLastLoginAt(user.getLastLoginAt());
            response.setLastLogoutAt(user.getLastLogoutAt());
            logger.info("Login successful for {}", user.getEmail());
        } else {
            response.setSuccess(false);
            response.setMessage("Invalid email or password");
            logger.warn("Login failed for {}", request.getEmail());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<UserDTO> logout(@RequestBody LogoutRequestDTO request){
        User user = userService.getUserEntityById(request.getUserId());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setLastLogoutAt(LocalDateTime.now());
        userService.save(user);

        UserDTO dto = UserMapper.mapToUserDTO(user);
        return ResponseEntity.ok(dto);
    }
}
