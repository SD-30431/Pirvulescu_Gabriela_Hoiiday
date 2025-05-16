package com.example.hoiiday.DTO;

import com.example.hoiiday.model.enums.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRoomDTO {
    @NotNull
    private RoomType roomType;
    @Min(1)
    private int qtyTotal;
    @DecimalMin("0.01")
    private BigDecimal price;
}
