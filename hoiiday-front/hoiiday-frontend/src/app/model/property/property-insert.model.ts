import { PropertyRoomDTO } from '../property-room.model';
export interface AvailabilityDTO {
  startDate: string;
  endDate:   string;
}
export interface PropertyInsertDTO {
  hostId:       number;
  name:         string;
  description:  string;
  maxGuests:    number;
  propertyRooms: PropertyRoomDTO[];
  ruleIds:      number[];
  imageUrls:    string[];
  location:     number;
  availability: AvailabilityDTO[];
}
