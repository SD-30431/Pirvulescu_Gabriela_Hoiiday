package com.example.hoiiday.service;

import com.example.hoiiday.DTO.PlanningRoomDTO;
import com.example.hoiiday.DTO.RoomMemberDTO;
import com.example.hoiiday.model.User;

import java.util.List;

public interface PlanningRoomService {
    PlanningRoomDTO createRoom(String name, Long ownerId);
    List<PlanningRoomDTO> getRoomsForUser(Long userId);
    RoomMemberDTO inviteUser(Long roomId, Long userId);
    RoomMemberDTO respondToInvite(Long roomId, Long userId, boolean accept);
    List<RoomMemberDTO> getRoomMembers(Long roomId);
}
