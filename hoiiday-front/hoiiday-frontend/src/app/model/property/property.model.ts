import { PropertyRoomDTO } from "../property-room.model";

// src/app/model/property/property.model.ts
export interface Property {
  propertyId: number;
  name: string;
  description: string;
  location: {
    address?: string;
    city: string;
    country: string;
  };
  maxGuests: number;
  propertyRooms: PropertyRoomDTO[]; 
  imageUrls?: string[];
  amenities?: string[];
  rating?: number;
  reviews?: Review[];
  lowestPrice?: number;
}

export interface Review {
  id: number;
  userId: number;
  userName: string;
  rating: number;
  comment: string;
  date: Date;
}
