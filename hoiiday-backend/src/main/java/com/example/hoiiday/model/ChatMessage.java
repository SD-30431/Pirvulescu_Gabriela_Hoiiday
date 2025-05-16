// com/example/hoiiday/model/ChatMessage.java
package com.example.hoiiday.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name="room_id")
    PlanningRoom room;

    @ManyToOne
    @JoinColumn(name="sender_id")
    User sender;

    String content;
    LocalDateTime timestamp;
}
