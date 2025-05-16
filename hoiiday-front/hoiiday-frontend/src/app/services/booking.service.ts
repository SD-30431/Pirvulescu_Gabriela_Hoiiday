import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BookingRequest {
  userId: number;
  roomType: 'SINGLE'|'DOUBLE'|'LUXURY';
  rooms: number;
  from: string;  // yyyy-MM-dd
  to:   string;
}

export interface BookingResponse {
  bookingId:  number;
  propertyId: number;
  userId:     number;
  checkIn:    string;
  checkOut:   string;
  totalPrice: number;
}

@Injectable({ providedIn: 'root' })
export class BookingService {
  private readonly API = 'http://localhost:8080/api/properties';
  constructor(private http: HttpClient) {}

  book(propertyId: number, req: BookingRequest): Observable<BookingResponse> {
    return this.http.post<BookingResponse>(
      `${this.API}/${propertyId}/bookings`,
      req
    );
  }
}
