package com.example.hoiiday.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final String apiKey;

    public TestController(@Value("${openai.api.key}") String apiKey) {
        // Mask most of the key for security
        this.apiKey = maskApiKey(apiKey);
    }

    @GetMapping("/config")
    public String testConfig() {
        return "API Key loaded: " + apiKey;
    }

    private String maskApiKey(String key) {
        if (key == null || key.length() < 10) {
            return "Invalid key";
        }
        // Show only first 5 and last 4 characters
        return key.substring(0, 5) + "..." + key.substring(key.length() - 4);
    }
}