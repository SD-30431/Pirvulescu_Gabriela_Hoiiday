package com.example.hoiiday.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {
    private Long propertyId;
    private Long hostId;
    private String name;
    private String description;
    private int maxGuests;
    private List<PropertyRoomDTO> propertyRooms;
    private List<Long> bookingIds;
    private List<Long> propertyRuleIds;
    private List<String> imageUrls;
    private LocationDTO location;
    private List<AvailabilityDTO> availability;
    private BigDecimal lowestPrice;
}
