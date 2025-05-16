package com.example.hoiiday.controller;

import com.example.hoiiday.model.Booking;
import com.example.hoiiday.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/properties/{propertyId}/bookings")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> book(
            @PathVariable Long propertyId,
            @Valid @RequestBody BookingRequest req
    ) {
        Booking b = bookingService.createBooking(
                propertyId,
                req.userId(),
                req.roomType(),
                req.rooms(),
                req.from(),
                req.to()
        );
        return new ResponseEntity<>(new BookingResponse(b), HttpStatus.CREATED);
    }
}
