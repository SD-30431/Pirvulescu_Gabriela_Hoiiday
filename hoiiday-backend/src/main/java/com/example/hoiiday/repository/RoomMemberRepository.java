package com.example.hoiiday.repository;

import com.example.hoiiday.model.PlanningRoom;
import com.example.hoiiday.model.RoomMember;
import com.example.hoiiday.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findByUser(User u);
    List<RoomMember> findByRoom(PlanningRoom r);
    Optional<RoomMember> findByRoomAndUser(PlanningRoom r, User u);
}
