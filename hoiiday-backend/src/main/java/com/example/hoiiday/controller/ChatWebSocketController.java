package com.example.hoiiday.controller;

import com.example.hoiiday.DTO.ChatMessageDTO;
import com.example.hoiiday.service.ChatPlanService;
import com.example.hoiiday.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    private final ChatPlanService svc;

    public ChatWebSocketController(ChatPlanService svc) {
        this.svc = svc;
    }

    @MessageMapping("/rooms/{roomId}/chat")
    public void onMessage(ChatMessageDTO msg) {
        svc.saveAndBroadcastMessage(msg);
    }
}
