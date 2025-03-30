package com.example.hoiiday.controller;

import com.example.hoiiday.DTO.LoginRequestDTO;
import com.example.hoiiday.DTO.LoginResponseDTO;
import com.example.hoiiday.model.User;
import com.example.hoiiday.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
            response.setSuccess(true);
            response.setRole(user.getRole());
            response.setMessage("Login successful");
            logger.info("Login successful");
        } else {
            response.setSuccess(false);
            response.setMessage("Invalid email or password");
            logger.info("Login failed, sent password: {}, stored password: {}", request.getPassword(), user.getPassword());
        }

        return ResponseEntity.ok(response);
    }
}