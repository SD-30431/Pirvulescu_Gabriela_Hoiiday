package com.example.hoiiday.model;

import jakarta.persistence.*;

@Entity
@Table(name = "property_room")
public class PropertyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_room_id")
    private Long propertyRoomId;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "no_of_rooms")
    private int numberOfRooms;
}


