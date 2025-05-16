package com.example.hoiiday.service;

import com.example.hoiiday.DTO.ChatMessageDTO;
import reactor.core.publisher.Mono;

public interface ChatService {
    Mono<String> getChatGptResponse(String prompt);
}
