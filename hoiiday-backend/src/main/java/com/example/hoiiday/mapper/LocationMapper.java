package com.example.hoiiday.mapper;

import com.example.hoiiday.DTO.LocationDTO;
import com.example.hoiiday.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDTO toDTO(Location location);
    Location toEntity(LocationDTO dto);
}
