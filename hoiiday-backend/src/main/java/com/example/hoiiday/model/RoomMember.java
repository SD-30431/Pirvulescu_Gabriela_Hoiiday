package com.example.hoiiday.model;

import com.example.hoiiday.model.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="room_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomMember {
    @Id @GeneratedValue
    Long id;
    @ManyToOne @JoinColumn(name="room_id")
    PlanningRoom room;
    @ManyToOne @JoinColumn(name="user_id")
    User user;
    @Enumerated(EnumType.STRING)
    MemberStatus status;
}