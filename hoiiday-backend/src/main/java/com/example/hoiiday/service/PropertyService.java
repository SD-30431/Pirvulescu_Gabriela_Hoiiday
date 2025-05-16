package com.example.hoiiday.service;

import com.example.hoiiday.DTO.PropertyDTO;
import com.example.hoiiday.DTO.PropertyInsertDTO;

import java.math.BigDecimal;
import java.util.List;

public interface PropertyService {
    List<PropertyDTO> getAllProperties();
    PropertyDTO getPropertyById(Long id);
    PropertyDTO createProperty(PropertyInsertDTO propertyInsertDTO);
    PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO);
    void deleteProperty(Long id);
    BigDecimal getLowestPriceForProperty(Long propertyId);
}
