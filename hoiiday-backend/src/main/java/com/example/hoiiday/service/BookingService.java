package com.example.hoiiday.service;

import com.example.hoiiday.model.Booking;
import com.example.hoiiday.model.enums.RoomType;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    /**
     * Creates a new booking for a property
     *
     * @param propertyId the ID of the property to book
     * @param userId the ID of the user making the booking
     * @param type the type of room to book
     * @param rooms the number of rooms to book
     * @param from the check-in date
     * @param to the check-out date
     * @return the created booking
     */
    Booking createBooking(Long propertyId,
                          Long userId,
                          RoomType type,
                          int rooms,
                          LocalDate from,
                          LocalDate to);

    /**
     * Retrieves all bookings for a property
     *
     * @param propertyId the ID of the property
     * @return list of bookings for the property
     */
    List<Booking> getBookingsByPropertyId(Long propertyId);
}