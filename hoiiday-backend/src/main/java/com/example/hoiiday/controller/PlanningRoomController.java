package com.example.hoiiday.controller;

import com.example.hoiiday.DTO.PlanningRoomDTO;
import com.example.hoiiday.DTO.RoomMemberDTO;
import com.example.hoiiday.model.User;
import com.example.hoiiday.service.PlanningRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planning-rooms")
public class PlanningRoomController {
    private final PlanningRoomService svc;

    public PlanningRoomController(PlanningRoomService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<PlanningRoomDTO> create(@RequestParam String name, @RequestParam Long ownerId) {
        return ResponseEntity.ok(svc.createRoom(name, ownerId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PlanningRoomDTO>> list(@PathVariable Long userId) {
        return ResponseEntity.ok(svc.getRoomsForUser(userId));
    }

    @PostMapping("/{roomId}/invite/{userId}")
    public ResponseEntity<RoomMemberDTO> invite(@PathVariable Long roomId, @PathVariable Long userId) {
        return ResponseEntity.ok(svc.inviteUser(roomId, userId));
    }

    @PostMapping("/{roomId}/respond/{userId}")
    public ResponseEntity<RoomMemberDTO> respond(@PathVariable Long roomId, @PathVariable Long userId, @RequestParam boolean accept) {
        return ResponseEntity.ok(svc.respondToInvite(roomId, userId, accept));
    }

    @GetMapping("/{roomId}/members")
    public ResponseEntity<List<RoomMemberDTO>> members(@PathVariable Long roomId) {
        return ResponseEntity.ok(svc.getRoomMembers(roomId));
    }
}
