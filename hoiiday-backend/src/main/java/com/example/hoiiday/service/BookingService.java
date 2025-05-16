package com.example.hoiiday.service;

import com.example.hoiiday.model.Booking;
import com.example.hoiiday.model.enums.RoomType;

import java.time.LocalDate;

public interface BookingService {
    Booking createBooking(Long propertyId,
                                 Long userId,
                                 RoomType type,
                                 int rooms,
                                 LocalDate from,
                                 LocalDate to);
}
