package com.example.hoiiday.mapper;
import com.example.hoiiday.DTO.ChatMessageDTO;
import com.example.hoiiday.model.ChatMessage;

public class ChatMessageMapper {
    public static ChatMessageDTO toDTO(ChatMessage m) {
        return ChatMessageDTO.builder()
                .id(m.getId())
                .roomId(m.getRoom().getId())
                .senderId(m.getSender().getUserId())
                .content(m.getContent())
                .timestamp(m.getTimestamp())
                .build();
    }
}
