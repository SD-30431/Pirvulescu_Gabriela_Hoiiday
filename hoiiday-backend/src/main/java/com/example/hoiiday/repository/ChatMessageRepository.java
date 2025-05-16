package com.example.hoiiday.repository;

import com.example.hoiiday.model.ChatMessage;
import com.example.hoiiday.model.PlanningRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRoomOrderByTimestampAsc(PlanningRoom r);
}
