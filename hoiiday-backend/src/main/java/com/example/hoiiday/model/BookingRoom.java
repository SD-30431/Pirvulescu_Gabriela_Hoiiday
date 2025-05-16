package com.example.hoiiday.model;

import com.example.hoiiday.model.enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_room")
@Getter
@Setter
public class BookingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private int qty;
    private BigDecimal price;
}
