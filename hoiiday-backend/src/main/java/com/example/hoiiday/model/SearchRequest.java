package com.example.hoiiday.model;

import com.example.hoiiday.model.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record SearchRequest(
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate from,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate to,
        RoomType type,
        int rooms) { }

