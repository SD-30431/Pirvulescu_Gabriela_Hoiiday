package com.example.hoiiday.mapper;
import com.example.hoiiday.DTO.PlanningRoomDTO;
import com.example.hoiiday.model.PlanningRoom;
import java.util.stream.Collectors;

public class PlanningRoomMapper {
    public static PlanningRoomDTO toDTO(PlanningRoom r) {
        return PlanningRoomDTO.builder()
                .id(r.getId())
                .name(r.getName())
                .ownerId(r.getOwner().getUserId())
                .createdAt(r.getCreatedAt())
                .memberIds(
                        r.getMembers()
                                .stream()
                                .map(m->m.getUser().getUserId())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
