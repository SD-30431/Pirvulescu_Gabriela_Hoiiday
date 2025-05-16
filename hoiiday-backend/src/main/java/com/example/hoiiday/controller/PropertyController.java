package com.example.hoiiday.controller;

import com.example.hoiiday.DTO.PropertyDTO;
import com.example.hoiiday.DTO.PropertyInsertDTO;
import com.example.hoiiday.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "http://localhost:4200")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        List<PropertyDTO> properties = propertyService.getAllProperties();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id) {
        PropertyDTO property = propertyService.getPropertyById(id);

        if (property != null) {
            BigDecimal lowestPrice = propertyService.getLowestPriceForProperty(id);
            property.setLowestPrice(lowestPrice);
            return new ResponseEntity<>(property, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PropertyDTO> createProperty(@Valid @RequestBody PropertyInsertDTO propertyInsertDTO) {
        PropertyDTO createdProperty = propertyService.createProperty(propertyInsertDTO);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO) {
        PropertyDTO updatedProperty = propertyService.updateProperty(id, propertyDTO);
        if (updatedProperty != null) {
            return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        try {
            propertyService.deleteProperty(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}