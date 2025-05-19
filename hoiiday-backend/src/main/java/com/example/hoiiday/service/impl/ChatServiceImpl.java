package com.example.hoiiday.service.impl;

import com.example.hoiiday.service.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {

    private final WebClient webClient;
    private final String apiKey;

    public ChatServiceImpl(WebClient.Builder builder) {
        this.apiKey = "";
        this.webClient = builder
                .baseUrl("https://api.openai.com")
                .build();
    }

    @Override
    public Mono<String> getChatGptResponse(String prompt) {
        var body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        return webClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)

                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(ex -> ex.getMessage().contains("429"))
                        .maxBackoff(Duration.ofSeconds(10)))
                .map(response -> {
                    System.out.println("API Response: " + response);

                    var choices = (List<?>) response.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        var first = (Map<?, ?>) choices.get(0);
                        var message = (Map<?, ?>) first.get("message");
                        return (String) message.get("content");
                    }
                    return "No response";
                })
                .onErrorResume(e -> {

                    System.err.println("Error calling OpenAI API: " + e.getMessage());
                    e.printStackTrace();
                    return Mono.just("Error: " + e.getMessage());
                });
    }
}