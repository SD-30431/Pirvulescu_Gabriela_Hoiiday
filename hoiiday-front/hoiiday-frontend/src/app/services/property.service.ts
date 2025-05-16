import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Property } from '../model/property/property.model';
import { PropertyInsertDTO } from '../model/property/property-insert.model';

export interface PropertyWithPrice extends Property {
  lowestPrice: number;
}

@Injectable({
  providedIn: 'root',
})
export class PropertyService {
  private apiUrl = 'http://localhost:8080/api/properties';

  constructor(private http: HttpClient) {}

  getAllProperties(): Observable<Property[]> {
    return this.http.get<Property[]>(this.apiUrl);
  }

  getPropertyById(id: number): Observable<Property> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Property>(url);
  }

  createProperty(property: PropertyInsertDTO): Observable<Property> {
    return this.http.post<Property>(this.apiUrl, property);
  }

  updateProperty(property: Property): Observable<Property> {
    const url = `${this.apiUrl}/${property.propertyId}`;
    return this.http.put<Property>(url, property);
  }

  deleteProperty(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
  
}
