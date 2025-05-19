package com.example.hoiiday.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class BookingXml {
    @XmlElement(name = "roomSelection")
    private RoomSelectionXml roomSelection;
    @XmlElement(name = "dates")
    private DatesXml dates;
    @XmlElement(name = "totalPrice")
    private BigDecimal totalPrice;

    public BookingXml() {}

    public BookingXml(RoomSelectionXml roomSelection, DatesXml dates, BigDecimal totalPrice) {
        this.roomSelection = roomSelection;
        this.dates = dates;
        this.totalPrice = totalPrice;
    }

    public RoomSelectionXml getRoomSelection() {
        return roomSelection;
    }

    public void setRoomSelection(RoomSelectionXml roomSelection) {
        this.roomSelection = roomSelection;
    }

    public DatesXml getDates() {
        return dates;
    }

    public void setDates(DatesXml dates) {
        this.dates = dates;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}