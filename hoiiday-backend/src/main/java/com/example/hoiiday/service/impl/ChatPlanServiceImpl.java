package com.example.hoiiday.service.impl;

import com.example.hoiiday.DTO.ChatMessageDTO;
import com.example.hoiiday.mapper.ChatMessageMapper;
import com.example.hoiiday.model.*;
import com.example.hoiiday.repository.*;
import com.example.hoiiday.service.ChatPlanService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatPlanServiceImpl implements ChatPlanService {
    private final ChatMessageRepository msgRepo;
    private final PlanningRoomRepository roomRepo;
    private final UserRepository userRepo;
    private final SimpMessagingTemplate ws;

    public ChatPlanServiceImpl(ChatMessageRepository msgRepo,
                           PlanningRoomRepository roomRepo,
                           UserRepository userRepo,
                           SimpMessagingTemplate ws) {
        this.msgRepo = msgRepo;
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
        this.ws = ws;
    }

    @Override
    public ChatMessageDTO saveAndBroadcastMessage(ChatMessageDTO dto) {
        PlanningRoom room = roomRepo.findById(dto.getRoomId()).orElseThrow();
        User sender = userRepo.findById(dto.getSenderId()).orElseThrow();

        ChatMessage m = ChatMessage.builder()
                .room(room)
                .sender(sender)
                .content(dto.getContent())
                .timestamp(dto.getTimestamp() != null
                        ? dto.getTimestamp()
                        : LocalDateTime.now())
                .build();
        m = msgRepo.save(m);

        ChatMessageDTO out = ChatMessageMapper.toDTO(m);
        ws.convertAndSend("/topic/rooms/" + dto.getRoomId() + "/chat", out);
        return out;
    }

    @Override
    public List<ChatMessageDTO> getHistory(Long roomId) {
        PlanningRoom room = roomRepo.findById(roomId).orElseThrow();
        return msgRepo.findByRoomOrderByTimestampAsc(room).stream()
                .map(ChatMessageMapper::toDTO)
                .collect(Collectors.toList());
    }
}
