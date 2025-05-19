package com.example.hoiiday.xml;

import com.example.hoiiday.DTO.LocationDTO;
import com.example.hoiiday.model.Location;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyXml {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "location")
    private LocationDTO location;
    @XmlElement(name = "description")
    private String description;
    // Add rules and policy if needed

    public PropertyXml() {}

    public PropertyXml(String name, LocationDTO location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}