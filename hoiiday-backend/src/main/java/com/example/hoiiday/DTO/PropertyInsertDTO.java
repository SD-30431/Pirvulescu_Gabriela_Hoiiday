package com.example.hoiiday.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyInsertDTO {
    private Long hostId;
    @NotBlank
    @Size(max=100)
    private String name;
    @NotBlank
    @Size(max=1000)
    private String description;
    @Min(1)
    @Max(30)
    private int maxGuests;
    @NotEmpty
    private List<PropertyRoomDTO> propertyRooms;
    @NotEmpty
    private List<Long> ruleIds;
    private List<String> imageUrls;
    private Long location;
    private List<AvailabilityDTO> availability;
}
