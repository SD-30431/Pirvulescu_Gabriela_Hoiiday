package com.example.hoiiday.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
