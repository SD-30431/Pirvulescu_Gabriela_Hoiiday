package com.example.hoiiday.service.impl;

import com.example.hoiiday.DTO.LocationDTO;
import com.example.hoiiday.mapper.LocationMapper;
import com.example.hoiiday.model.Location;
import com.example.hoiiday.repository.LocationRepository;
import com.example.hoiiday.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(locationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        return locationRepository.findById(id)
                .map(locationMapper::toDTO)
                .orElse(null);
    }

    @Override
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = locationMapper.toEntity(locationDTO);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.toDTO(savedLocation);
    }

    @Override
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        return locationRepository.findById(id)
                .map(existingLocation -> {
                    existingLocation.setCity(locationDTO.getCity());
                    existingLocation.setCountry(locationDTO.getCountry());
                    existingLocation.setAddress(locationDTO.getAddress());
                    Location updatedLocation = locationRepository.save(existingLocation);
                    return locationMapper.toDTO(updatedLocation);
                })
                .orElse(null);
    }

    @Override
    public void deleteLocation(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Location not found with id: " + id);
        }
    }
}