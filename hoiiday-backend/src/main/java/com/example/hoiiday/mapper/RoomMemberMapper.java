package com.example.hoiiday.mapper;
import com.example.hoiiday.DTO.RoomMemberDTO;
import com.example.hoiiday.model.RoomMember;

public class RoomMemberMapper {
    public static RoomMemberDTO toDTO(RoomMember m) {
        return RoomMemberDTO.builder()
                .id(m.getId())
                .roomId(m.getRoom().getId())
                .userId(m.getUser().getUserId())
                .status(m.getStatus())
                .build();
    }
}
