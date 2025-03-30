package com.example.hoiiday.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to Hoiiday Backend";
    }

    @GetMapping("/api/health")
    public String healthCheck() {
        return "Backend is running successfully";
    }
}