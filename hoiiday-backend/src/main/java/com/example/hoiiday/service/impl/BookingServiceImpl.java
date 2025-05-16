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
        if (!from.isBefore(to)) {
            throw new IllegalArgumentException("check-in must be before check-out");
        }

        PropertyRoom pr = propertyRoomRepository
                .findByProperty_PropertyIdAndRoomType(propertyId, type)
                .orElseThrow(() -> new IllegalArgumentException("Room type not offered"));

        propertyAvailabilityRepository
                .windowCovering(propertyId, from, to.minusDays(1))
                .orElseThrow(() -> new IllegalStateException("Property closed"));

        int alreadyBooked = bookingRoomRepository.qtyAlreadyBooked(propertyId, type, from, to);
        if (pr.getQtyTotal() - alreadyBooked < rooms) {
            throw new IllegalStateException("Not enough rooms left");
        }

        long nights = ChronoUnit.DAYS.between(from, to);
        BigDecimal total = pr.getPrice()
                .multiply(BigDecimal.valueOf(rooms))
                .multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking();
        booking.setProperty(pr.getProperty());
        booking.setUser(userRepository.getReferenceById(userId));
        booking.setCheckIn(from);
        booking.setCheckOut(to);
        booking.setTotalPrice(total);

        BookingRoom br = new BookingRoom();
        br.setBooking(booking);
        br.setRoomType(type);
        br.setQty(rooms);
        br.setPrice(pr.getPrice());

        booking.getRooms().add(br);

        return bookingRepository.save(booking);
    }
}
