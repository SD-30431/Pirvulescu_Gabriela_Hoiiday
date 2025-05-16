package com.example.hoiiday.DTO;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {
    private Long id;
    private Long roomId;
    private Long senderId;
    private String content;
    private LocalDateTime timestamp;
}
