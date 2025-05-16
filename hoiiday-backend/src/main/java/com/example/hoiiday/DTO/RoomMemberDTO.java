package com.example.hoiiday.DTO;
import com.example.hoiiday.model.enums.MemberStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomMemberDTO {
    private Long id;
    private Long roomId;
    private Long userId;
    private MemberStatus status;
}
