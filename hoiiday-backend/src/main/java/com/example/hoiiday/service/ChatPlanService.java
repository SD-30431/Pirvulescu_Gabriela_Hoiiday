package com.example.hoiiday.service;
import com.example.hoiiday.DTO.ChatMessageDTO;

import java.util.List;

public interface ChatPlanService {
    ChatMessageDTO saveAndBroadcastMessage(ChatMessageDTO dto);
    List<ChatMessageDTO> getHistory(Long roomId);
}
