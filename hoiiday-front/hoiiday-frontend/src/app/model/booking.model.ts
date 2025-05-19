// src/app/model/booking.model.ts
export interface BookingRequest {
  userId: number;
  roomType: 'STANDARD' | 'DELUXE' | 'SUITE' | string;
  rooms: number;
  from: string;
  to: string;
}

export interface BookingResponse {
  bookingId: number;
  propertyId: number;
  userId: number;
  checkIn: string;
  checkOut: string;
  rooms: RoomBooking[];
  totalPrice: number;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface RoomBooking {
  roomType: string;
  qty: number;
  price: number;
}

// XML structure interfaces to help with typing
export interface BookingXmlResponse {
  bookingDetails: {
    property: PropertyXml;
    booking: BookingXml;
  };
}

export interface PropertyXml {
  name: string;
  location: {
    city: string;
    country: string;
  };
  description: string;
}

export interface BookingXml {
  roomSelection: {
    roomType: string;
    numberOfRooms: number;
    pricePerNight: number;
  };
  dates: {
    checkIn: string;
    checkOut: string;
    lengthOfStay: number;
  };
  totalPrice: number;
}