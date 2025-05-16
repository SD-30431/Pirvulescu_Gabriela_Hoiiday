package com.example.hoiiday.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Long locationId;
    private String city;
    private String country;
    private String address;
}
