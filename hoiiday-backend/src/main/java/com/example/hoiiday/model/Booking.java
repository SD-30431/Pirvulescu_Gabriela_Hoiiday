package com.example.hoiiday.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private User user;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private BigDecimal totalPrice;
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingRoom> rooms = new ArrayList<>();
}

