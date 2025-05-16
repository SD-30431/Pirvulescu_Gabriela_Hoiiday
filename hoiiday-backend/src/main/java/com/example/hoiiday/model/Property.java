package com.example.hoiiday.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "host_id")
    private Long hostId;

    @Column(name = "name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "max_guests")
    private int maxGuests;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<PropertyRoom> propertyRoomList;

    @OneToMany(mappedBy = "property")
    private List<Booking> bookingsList;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<PropertyRules> propertyRulesList;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyImages> propertyImages;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyAvailability> availabilityWindows = new ArrayList<>();
    /**
     * Calculate the lowest price from the property rooms.
     * @return the lowest price, or BigDecimal.ZERO if no price is found.
     */
    public BigDecimal getLowestPrice() {
        return propertyRoomList.stream()
                .map(PropertyRoom::getPrice)    // Get price of each room
                .min(BigDecimal::compareTo)     // Get the minimum price
                .orElse(BigDecimal.ZERO);       // Return BigDecimal.ZERO if no price exists
    }
}
