package com.example.hoiiday.service.impl;

import com.example.hoiiday.DTO.PlanningRoomDTO;
import com.example.hoiiday.DTO.RoomMemberDTO;
import com.example.hoiiday.mapper.PlanningRoomMapper;
import com.example.hoiiday.mapper.RoomMemberMapper;
import com.example.hoiiday.model.*;
import com.example.hoiiday.model.enums.MemberStatus;
import com.example.hoiiday.repository.PlanningRoomRepository;
import com.example.hoiiday.repository.RoomMemberRepository;
import com.example.hoiiday.repository.UserRepository;
import com.example.hoiiday.service.PlanningRoomService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanningRoomServiceImpl implements PlanningRoomService {
    private final PlanningRoomRepository roomRepo;
    private final RoomMemberRepository memberRepo;
    private final UserRepository userRepo;
    private final SimpMessagingTemplate ws;

    public PlanningRoomServiceImpl(PlanningRoomRepository roomRepo,
                                   RoomMemberRepository memberRepo,
                                   UserRepository userRepo,
                                   SimpMessagingTemplate ws) {
        this.roomRepo = roomRepo;
        this.memberRepo = memberRepo;
        this.userRepo = userRepo;
        this.ws = ws;
    }

    @Override
    @Transactional
    public PlanningRoomDTO createRoom(String name, Long ownerId) {
        User owner = userRepo.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("No such user"));
        PlanningRoom room = PlanningRoom.builder()
                .name(name)
                .owner(owner)
                .createdAt(LocalDateTime.now())
                .build();
        room = roomRepo.save(room);

        RoomMember me = RoomMember.builder()
                .room(room)
                .user(owner)
                .status(MemberStatus.ACCEPTED)
                .build();
        memberRepo.save(me);
        room.setMembers(new ArrayList<>(List.of(me)));
        return PlanningRoomMapper.toDTO(room);
    }

    @Override
    public List<PlanningRoomDTO> getRoomsForUser(Long userId) {
        User u = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return memberRepo.findByUser(u).stream()
                .map(RoomMember::getRoom)
                .map(PlanningRoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoomMemberDTO inviteUser(Long roomId, Long userId) {
        PlanningRoom room = roomRepo.findById(roomId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();

        RoomMember inv = RoomMember.builder()
                .room(room)
                .user(user)
                .status(MemberStatus.INVITED)
                .build();
        inv = memberRepo.save(inv);

        ws.convertAndSend(
                "/topic/invitations/" + userId,
                RoomMemberMapper.toDTO(inv)
        );
        return RoomMemberMapper.toDTO(inv);
    }

    @Override
    @Transactional
    public RoomMemberDTO respondToInvite(Long roomId, Long userId, boolean accept) {
        PlanningRoom room = roomRepo.findById(roomId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();
        RoomMember m = memberRepo.findByRoomAndUser(room, user)
                .orElseThrow();

        m.setStatus(accept ? MemberStatus.ACCEPTED : MemberStatus.DECLINED);
        m = memberRepo.save(m);

        ws.convertAndSend(
                "/topic/rooms/" + roomId + "/members",
                RoomMemberMapper.toDTO(m)
        );
        return RoomMemberMapper.toDTO(m);
    }

    @Override
    public List<RoomMemberDTO> getRoomMembers(Long roomId) {
        PlanningRoom room = roomRepo.findById(roomId).orElseThrow();
        return memberRepo.findByRoom(room).stream()
                .map(RoomMemberMapper::toDTO)
                .collect(Collectors.toList());
    }
}
