package com.example.hoiiday.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class DatesXml {
    @XmlElement(name = "checkIn")
    private LocalDate checkIn;
    @XmlElement(name = "checkOut")
    private LocalDate checkOut;
    @XmlElement(name = "lengthOfStay")
    private int lengthOfStay;

    public DatesXml() {}

    public DatesXml(LocalDate checkIn, LocalDate checkOut, int lengthOfStay) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.lengthOfStay = lengthOfStay;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getLengthOfStay() {
        return lengthOfStay;
    }

    public void setLengthOfStay(int lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }
}