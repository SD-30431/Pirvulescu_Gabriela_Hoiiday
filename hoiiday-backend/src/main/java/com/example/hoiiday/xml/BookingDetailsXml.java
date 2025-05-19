package com.example.hoiiday.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bookingDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookingDetailsXml {
    @XmlElement(name = "property")
    private PropertyXml property;
    @XmlElement(name = "booking")
    private BookingXml booking;

    public BookingDetailsXml() {}

    public BookingDetailsXml(PropertyXml property, BookingXml booking) {
        this.property = property;
        this.booking = booking;
    }

    public PropertyXml getProperty() {
        return property;
    }

    public void setProperty(PropertyXml property) {
        this.property = property;
    }

    public BookingXml getBooking() {
        return booking;
    }

    public void setBooking(BookingXml booking) {
        this.booking = booking;
    }
}