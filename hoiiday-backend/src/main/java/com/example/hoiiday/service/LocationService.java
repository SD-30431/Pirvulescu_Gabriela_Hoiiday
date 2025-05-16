package com.example.hoiiday.service;

import com.example.hoiiday.DTO.LocationDTO;

import java.util.List;

public interface LocationService {
    List<LocationDTO> getAllLocations();
    LocationDTO getLocationById(Long id);
    LocationDTO createLocation(LocationDTO locationDTO);
    LocationDTO updateLocation(Long id, LocationDTO locationDTO);
    void deleteLocation(Long id);
}