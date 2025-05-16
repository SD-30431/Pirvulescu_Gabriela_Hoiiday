package com.example.hoiiday.service.impl;

import com.example.hoiiday.DTO.*;
import com.example.hoiiday.mapper.PropertyMapper;
import com.example.hoiiday.model.*;
import com.example.hoiiday.repository.*;
import com.example.hoiiday.service.PropertyService;
import jakarta.el.PropertyNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final RulesRepository rulesRepository;
    private final LocationRepository locationRepository;

    public PropertyServiceImpl(
            PropertyRepository propertyRepository,
            PropertyMapper propertyMapper,
            RulesRepository rulesRepository,
            LocationRepository locationRepository
    ) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper   = propertyMapper;
        this.rulesRepository   = rulesRepository;
        this.locationRepository = locationRepository;
    }

    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findAll().stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PropertyDTO getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .map(propertyMapper::toDTO)
                .orElse(null);
    }

    @Transactional
    public PropertyDTO createProperty(PropertyInsertDTO dto) {
        Property p = propertyMapper.fromInsertDTO(dto);
        if (dto.getLocation() != null) {
            Location loc = locationRepository.findById(dto.getLocation())
                    .orElseThrow();
            p.setLocation(loc);
        }
        if (dto.getRuleIds() != null) {
            List<PropertyRules> rl = dto.getRuleIds().stream()
                    .map(rid -> {
                        Rules r = rulesRepository.findById(rid).orElseThrow();
                        PropertyRules pr = new PropertyRules();
                        pr.setProperty(p);
                        pr.setRules(r);
                        return pr;
                    }).collect(Collectors.toList());
            p.setPropertyRulesList(rl);
        }
        if (dto.getImageUrls() != null) {
            List<PropertyImages> imgs = dto.getImageUrls().stream()
                    .map(u -> {
                        PropertyImages pi = new PropertyImages();
                        pi.setProperty(p);
                        pi.setImageUrl(u);
                        return pi;
                    }).collect(Collectors.toList());
            p.setPropertyImages(imgs);
        }
        if (dto.getPropertyRooms() != null) {
            List<PropertyRoom> rooms = dto.getPropertyRooms().stream()
                    .map(rdto -> {
                        PropertyRoom pr = new PropertyRoom();
                        pr.setProperty(p);
                        pr.setRoomType(rdto.getRoomType());
                        pr.setQtyTotal(rdto.getQtyTotal());
                        pr.setPrice(rdto.getPrice());
                        return pr;
                    }).collect(Collectors.toList());
            p.setPropertyRoomList(rooms);
        }
        if (dto.getAvailability() != null) {
            List<PropertyAvailability> av = new ArrayList<>();
            for (var a : dto.getAvailability()) {
                PropertyAvailability pa = new PropertyAvailability();
                pa.setProperty(p);
                pa.setStartDate(a.getStartDate());
                pa.setEndDate(a.getEndDate());
                av.add(pa);
            }
            p.setAvailabilityWindows(av);
        }
        return propertyMapper.toDTO(propertyRepository.save(p));
    }

    @Transactional
    public PropertyDTO updateProperty(Long id, PropertyDTO dto) {
        Property e = propertyRepository.findById(id).orElseThrow();
        e.setHostId(dto.getHostId());
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setMaxGuests(dto.getMaxGuests());
        propertyRepository.save(e);
        return propertyMapper.toDTO(e);
    }

    @Transactional
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
    // PropertyService.java

    public BigDecimal getLowestPriceForProperty(Long propertyId) {
        return propertyRepository.findById(propertyId)
                .map(Property::getLowestPrice)
                .orElse(BigDecimal.ZERO);
    }
}
