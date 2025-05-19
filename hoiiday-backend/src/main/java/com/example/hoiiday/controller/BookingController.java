package com.example.hoiiday.controller;

import com.example.hoiiday.DTO.PropertyDTO;
import com.example.hoiiday.model.Booking;
import com.example.hoiiday.service.BookingService;
import com.example.hoiiday.service.PropertyService;
import com.example.hoiiday.xml.*;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.example.hoiiday.model.PropertyRoom;
import jakarta.validation.Valid;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.hoiiday.model.Booking;
import com.example.hoiiday.model.Property;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/properties/{propertyId}/bookings")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    private final BookingService bookingService;

    private final PropertyService propertyService;

    public BookingController(BookingService bookingService, PropertyService propertyService) {
        this.bookingService = bookingService;
        this.propertyService = propertyService;
    }

    @PostMapping("/xml") // New endpoint for XML
    public ResponseEntity<String> bookAndGetXml(
            @PathVariable Long propertyId,
            @Valid @RequestBody BookingRequest req
    )
    {
        if (req.userId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        Booking b = bookingService.createBooking(
                propertyId,
                req.userId(),
                req.roomType(),
                req.rooms(),
                req.from(),
                req.to()
        );

        // Fetch property details
        PropertyDTO propertyDTO = propertyService.getPropertyById(propertyId);

        // Create XML structure
        BookingDetailsXml bookingDetailsXml = createBookingDetailsXml(b, propertyDTO);

        // Generate XML string
        String xml = generateXml(bookingDetailsXml);

        return ResponseEntity.ok(xml);
    }

    private BookingDetailsXml createBookingDetailsXml(Booking booking, PropertyDTO property) {
        PropertyXml propertyXml = new PropertyXml(
                property.getName(),
                property.getLocation(),
                property.getDescription()// You might want to get this from the property
        );

        RoomSelectionXml roomSelectionXml = new RoomSelectionXml(
                booking.getRooms().get(0).getRoomType().toString(), // Assuming one room type
                booking.getRooms().get(0).getQty(),
                booking.getRooms().get(0).getPrice()
        );

        DatesXml datesXml = new DatesXml(
                booking.getCheckIn(),
                booking.getCheckOut(),
                (int) java.time.temporal.ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut())
        );

        BookingXml bookingXml = new BookingXml(
                roomSelectionXml,
                datesXml,
                booking.getTotalPrice()
        );

        return new BookingDetailsXml(propertyXml, bookingXml);
    }

    private String generateXml(BookingDetailsXml bookingDetails) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(BookingDetailsXml.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            marshaller.marshal(bookingDetails, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Error generating XML", e);
        }
    }

    @PostMapping
    public ResponseEntity<BookingResponse> book(
            @PathVariable Long propertyId,
            @Valid @RequestBody BookingRequest req
    ) {
        // Log incoming request for debugging
        System.out.println("Received booking request: " + req.toString());

        if (req.userId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

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

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookingsByPropertyId(
            @PathVariable Long propertyId
    ) {
        List<Booking> bookings = bookingService.getBookingsByPropertyId(propertyId);
        List<BookingResponse> response = bookings.stream()
                .map(BookingResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}