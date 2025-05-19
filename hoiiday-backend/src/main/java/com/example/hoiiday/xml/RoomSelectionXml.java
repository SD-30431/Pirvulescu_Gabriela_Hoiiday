package com.example.hoiiday.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class RoomSelectionXml {
    @XmlElement(name = "roomType")
    private String roomType;
    @XmlElement(name = "numberOfRooms")
    private int numberOfRooms;
    @XmlElement(name = "pricePerNight")
    private BigDecimal pricePerNight;

    public RoomSelectionXml() {}

    public RoomSelectionXml(String roomType, int numberOfRooms, BigDecimal pricePerNight) {
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
}