package com.example.hoiiday.controller;

import com.example.hoiiday.DTO.*;
import com.example.hoiiday.model.User;
import com.example.hoiiday.configuration.security.JwtUtil;
import com.example.hoiiday.configuration.security.UserPrincipal;
import com.example.hoiiday.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = new LoginResponseDTO();

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Get user from database
            User user = userService.findUserByEmail(request.getEmail());

            // Update last login time
            user.markLogin();
            userService.save(user);

            // Create UserPrincipal for token generation
            UserPrincipal userPrincipal = new UserPrincipal(user);

            // Generate JWT token
            String token = jwtUtil.generateToken(userPrincipal);

            // Build response
            response.setSuccess(true);
            response.setMessage("Login successful");
            response.setToken(token);
            response.setUserId(user.getUserId());
            response.setRole(user.getRole());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setLastLoginAt(user.getLastLoginAt());
            response.setLastLogoutAt(user.getLastLogoutAt());

            logger.info("Login successful for {}", user.getEmail());

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Invalid email or password");
            logger.warn("Login failed for {}", request.getEmail());
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginResponseDTO> logout(@RequestBody LogoutRequestDTO req) {
        User user = userService.getUserEntityById(req.getUserId());

        // Mark logout time
        user.markLogout();
        userService.save(user);

        LoginResponseDTO resp = new LoginResponseDTO();
        resp.setSuccess(true);
        resp.setMessage("Logout successful");
        resp.setRole(user.getRole());
        resp.setFirstName(user.getFirstName());
        resp.setLastName(user.getLastName());
        resp.setLastLoginAt(user.getLastLoginAt());
        resp.setLastLogoutAt(user.getLastLogoutAt());
        logger.info("User {} logged out at {}", user.getEmail(), user.getLastLogoutAt());
        return ResponseEntity.ok(resp);
    }
}