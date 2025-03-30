package com.example.hoiiday.model;

import com.example.hoiiday.model.enums.RoomType;
import jakarta.persistence.*;


import java.util.List;


@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_type")
    private RoomType roomType;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "room")
    private List<PropertyRoom> propertyRoomList;
}

