package com.example.hoiiday.repository;

import com.example.hoiiday.model.BookingRoom;
import com.example.hoiiday.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRoomRepository extends JpaRepository<BookingRoom, Long> {
    @Query(
            "select coalesce(sum(br.qty),0) " +
                    "  from BookingRoom br " +
                    " where br.booking.property.propertyId = :pid " +
                    "   and br.roomType                   = :type " +
                    "   and br.booking.checkIn            < :to " +
                    "   and br.booking.checkOut           > :from"
    )
    int qtyAlreadyBooked(
            @Param("pid")  Long propertyId,
            @Param("type") RoomType type,
            @Param("from") LocalDate from,
            @Param("to")   LocalDate to
    );
}
