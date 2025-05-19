package com.example.hoiiday.controller;

import com.example.hoiiday.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/ask")
    public Mono<ResponseEntity<String>> askChatGet(@RequestParam(required = true) String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("Prompt cannot be empty"));
        }

        return chatService.getChatGptResponse(prompt)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error: " + e.getMessage())
                ));
    }

    @PostMapping("/ask")
    public Mono<ResponseEntity<String>> askChatPost(@RequestBody Map<String, String> requestBody) {
        String prompt = requestBody.get("prompt");
        if (prompt == null || prompt.trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("Prompt cannot be empty"));
        }

        return chatService.getChatGptResponse(prompt)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error: " + e.getMessage())
                ));
    }

    @GetMapping("/echo")
    public ResponseEntity<Map<String, String>> echo(@RequestParam(defaultValue = "Hello") String message) {
        return ResponseEntity.ok(Map.of(
                "message", message,
                "echo", "You said: " + message,
                "timestamp", String.valueOf(System.currentTimeMillis())
        ));
    }
}