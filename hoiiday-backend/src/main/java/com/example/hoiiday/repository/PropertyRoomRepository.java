package com.example.hoiiday.repository;

import com.example.hoiiday.model.PropertyRoom;
import com.example.hoiiday.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRoomRepository extends JpaRepository<PropertyRoom, Long> {
    Optional<PropertyRoom> findByProperty_PropertyIdAndRoomType(
            Long propertyId,
            RoomType roomType
    );
}