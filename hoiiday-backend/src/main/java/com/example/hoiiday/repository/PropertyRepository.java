package com.example.hoiiday.repository;

import com.example.hoiiday.model.Property;
import com.example.hoiiday.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("""
      SELECT p
        FROM Property p
        JOIN p.propertyRoomList pr
        LEFT JOIN BookingRoom br 
          ON br.booking.property = p
         AND br.roomType         = :type
         AND br.booking.checkIn  < :to
         AND br.booking.checkOut > :from
       WHERE pr.roomType = :type
         AND EXISTS (
             SELECT 1
               FROM PropertyAvailability pa
              WHERE pa.property   = p
                AND pa.startDate <= :from
                AND pa.endDate   >= :toMinusOne
         )
       GROUP BY p, pr.qtyTotal
      HAVING pr.qtyTotal >= COALESCE(SUM(br.qty),0) + :rooms
    """)
    List<Property> search(
            @Param("type")       RoomType   type,
            @Param("rooms")      int        rooms,
            @Param("from")       LocalDate  from,
            @Param("to")         LocalDate  to,
            @Param("toMinusOne") LocalDate  toMinusOne
    );
}
