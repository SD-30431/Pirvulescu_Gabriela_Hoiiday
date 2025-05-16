package com.example.hoiiday.DTO;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanningRoomDTO {
    private Long id;
    private String name;
    private Long ownerId;
    private LocalDateTime createdAt;
    private List<Long> memberIds;
}
