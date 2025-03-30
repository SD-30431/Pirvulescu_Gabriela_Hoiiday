package com.example.hoiiday.model;

import jakarta.persistence.*;


import java.util.List;


@Entity
@Table(name = "Property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "host_id")
    private Long hostID;

    @Column(name = "name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "max_guests")
    private int maxGuests;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<PropertyRoom> propertyRoomList;

    @OneToMany(mappedBy = "property")
    private List<Bookings> bookingsList;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<PropertyRules> propertyRulesList;


}
