// src/app/services/booking.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class BookingService {
  private readonly BASE = '/api/properties';

  constructor(private http: HttpClient) {}

  /**
   * Calls the XML endpoint and returns the raw XML string
   */
  bookAndGetXml(propertyId: number, requestBody: any): Observable<string> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/xml'
    });

    return this.http.post(
      `${this.BASE}/${propertyId}/bookings/xml`,
      requestBody,
      { headers, responseType: 'text' }
    );
  }
}
