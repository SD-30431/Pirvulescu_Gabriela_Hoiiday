package com.example.hoiiday.controller;

import com.example.hoiiday.model.Booking;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResponse(
        Long bookingId,
        Long propertyId,
        Long userId,
        LocalDate checkIn,
        LocalDate checkOut,
        BigDecimal totalPrice
) {
    public BookingResponse(Booking b) {
        this(
                b.getId(),
                b.getProperty().getPropertyId(),
                b.getUser().getUserId(),
                b.getCheckIn(),
                b.getCheckOut(),
                b.getTotalPrice()
        );
    }
}
