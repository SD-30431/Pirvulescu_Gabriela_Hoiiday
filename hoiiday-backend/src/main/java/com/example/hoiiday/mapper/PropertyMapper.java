package com.example.hoiiday.mapper;

import com.example.hoiiday.DTO.AvailabilityDTO;
import com.example.hoiiday.DTO.PropertyDTO;
import com.example.hoiiday.DTO.PropertyInsertDTO;
import com.example.hoiiday.DTO.PropertyRoomDTO;
import com.example.hoiiday.model.Property;
import com.example.hoiiday.model.PropertyAvailability;
import com.example.hoiiday.model.PropertyImages;
import com.example.hoiiday.model.PropertyRules;
import com.example.hoiiday.model.PropertyRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = { RulesMapper.class, LocationMapper.class })
public interface PropertyMapper {

    @Mapping(source = "propertyRoomList", target = "propertyRooms", qualifiedByName = "roomsToDtos")
    @Mapping(source = "propertyRulesList", target = "propertyRuleIds", qualifiedByName = "propertyRulesToRuleIds")
    @Mapping(source = "propertyImages", target = "imageUrls", qualifiedByName = "propertyImagesToUrls")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "availabilityWindows", target = "availability", qualifiedByName = "windowsToDtos")
    PropertyDTO toDTO(Property property);

    @Mapping(target = "propertyId", ignore = true)
    @Mapping(target = "propertyRoomList", ignore = true)
    @Mapping(target = "propertyRulesList", ignore = true)
    @Mapping(target = "propertyImages", ignore = true)
    @Mapping(target = "bookingsList", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "availabilityWindows", ignore = true)
    Property fromInsertDTO(PropertyInsertDTO insertDTO);

    @Named("roomsToDtos")
    default List<PropertyRoomDTO> roomsToDtos(List<PropertyRoom> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(r -> new PropertyRoomDTO(
                        r.getRoomType(),
                        r.getQtyTotal(),
                        r.getPrice()
                ))
                .collect(Collectors.toList());
    }

    @Named("propertyRulesToRuleIds")
    default List<Long> propertyRulesToRuleIds(List<PropertyRules> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(pr -> pr.getRules().getRuleId())
                .collect(Collectors.toList());
    }

    /**
     * Converts saved image paths (e.g. "/uploads/xyz.jpg") into full URLs
     * based on the current request context.
     */
    @Named("propertyImagesToUrls")
    default List<String> propertyImagesToUrls(List<PropertyImages> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(pi -> ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(pi.getImageUrl())
                        .toUriString()
                )
                .collect(Collectors.toList());
    }

    @Named("windowsToDtos")
    default List<AvailabilityDTO> windowsToDtos(List<PropertyAvailability> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(pa -> new AvailabilityDTO(pa.getStartDate(), pa.getEndDate()))
                .collect(Collectors.toList());
    }
}
