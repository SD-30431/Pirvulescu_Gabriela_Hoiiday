package com.example.hoiiday.controller;

import com.example.hoiiday.model.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingRequest(
        Long userId,
        @NotNull(message="roomType is required")
        RoomType roomType,

        @Min(value=1, message="You must book at least one room")
        int rooms,

        @NotNull @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate from,

        @NotNull @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate to
) { }
