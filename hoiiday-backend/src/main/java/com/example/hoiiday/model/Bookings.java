package com.example.hoiiday.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "bookings")
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "check_in")
    private Date checkInDate;

    @Column(name = "check_out")
    private Date checkOutDate;

    @Column(name = "guests")
    private int guests;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "created_at")
    private Date createdAt;

    
}
