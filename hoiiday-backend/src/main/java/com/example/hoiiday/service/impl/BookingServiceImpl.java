package com.example.hoiiday.service.impl;

import com.example.hoiiday.model.Booking;
import com.example.hoiiday.model.BookingRoom;
import com.example.hoiiday.model.PropertyRoom;
import com.example.hoiiday.model.enums.RoomType;
import com.example.hoiiday.repository.*;
import com.example.hoiiday.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final PropertyRoomRepository propertyRoomRepository;
    private final PropertyAvailabilityRepository propertyAvailabilityRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(PropertyRoomRepository propertyRoomRepository,
                              PropertyAvailabilityRepository propertyAvailabilityRepository,
                              BookingRoomRepository bookingRoomRepository,
                              BookingRepository bookingRepository,
                              UserRepository userRepository) {
        this.propertyRoomRepository = propertyRoomRepository;
        this.propertyAvailabilityRepository = propertyAvailabilityRepository;
        this.bookingRoomRepository = bookingRoomRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Booking createBooking(Long propertyId,
                                 Long userId,
                                 RoomType type,
                                 int rooms,
                                 LocalDate from,
                                 LocalDate to) {
        // Validate userId is not null
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        // Log params for debugging
        System.out.println("Creating booking: propertyId=" + propertyId +
                ", userId=" + userId +
                ", roomType=" + type +
                ", rooms=" + rooms +
                ", from=" + from +
                ", to=" + to);

        // Validate dates
        if (!from.isBefore(to)) {
            throw new IllegalArgumentException("check-in must be before check-out");
        }

        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }

        // Find the property room
        PropertyRoom pr = propertyRoomRepository
                .findByProperty_PropertyIdAndRoomType(propertyId, type)
                .orElseThrow(() -> new IllegalArgumentException("Room type not offered"));

        // Check property availability
        propertyAvailabilityRepository
                .windowCovering(propertyId, from, to.minusDays(1))
                .orElseThrow(() -> new IllegalStateException("Property closed"));

        // Check room availability
        int alreadyBooked = bookingRoomRepository.qtyAlreadyBooked(propertyId, type, from, to);
        if (pr.getQtyTotal() - alreadyBooked < rooms) {
            throw new IllegalStateException("Not enough rooms left");
        }

        // Calculate total price
        long nights = ChronoUnit.DAYS.between(from, to);
        BigDecimal total = pr.getPrice()
                .multiply(BigDecimal.valueOf(rooms))
                .multiply(BigDecimal.valueOf(nights));

        // Create booking
        Booking booking = new Booking();
        booking.setProperty(pr.getProperty());

        // Get the actual user entity instead of just a reference
        booking.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " does not exist")));

        booking.setCheckIn(from);
        booking.setCheckOut(to);
        booking.setTotalPrice(total);

        // Create booking room
        BookingRoom br = new BookingRoom();
        br.setBooking(booking);
        br.setRoomType(type);
        br.setQty(rooms);
        br.setPrice(pr.getPrice());

        booking.getRooms().add(br);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByPropertyId(Long propertyId) {
        return bookingRepository.findByProperty_PropertyId(propertyId);
    }
}