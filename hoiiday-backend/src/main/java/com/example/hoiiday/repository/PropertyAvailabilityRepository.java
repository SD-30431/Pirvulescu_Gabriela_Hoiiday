package com.example.hoiiday.repository;

import com.example.hoiiday.model.PropertyAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PropertyAvailabilityRepository extends JpaRepository<PropertyAvailability, Long> {
    @Query(
            "select pa " +
                    "  from PropertyAvailability pa " +
                    " where pa.property.propertyId = :pid " +
                    "   and pa.startDate <= :from " +
                    "   and pa.endDate   >= :toMinusOne"
    )
    Optional<PropertyAvailability> windowCovering(
            @Param("pid")        Long propertyId,
            @Param("from")       LocalDate from,
            @Param("toMinusOne") LocalDate toMinusOne
    );
}
