package com.example.hoiiday.model;

import jakarta.persistence.*;


import java.util.Date;

@Entity
@Table( name = "property_availability")
public class PropertyAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Long availabilityId;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date end_date;
}

