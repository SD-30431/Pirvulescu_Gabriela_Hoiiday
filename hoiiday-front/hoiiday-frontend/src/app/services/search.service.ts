import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SearchRequest } from "../model/searchreq.model";
import { Property } from "../model/property/property.model";

@Injectable({ providedIn: 'root' })
export class SearchService {
  private api = 'http://localhost:8080/api/search';

  constructor(private http: HttpClient) {}

  search(req: SearchRequest): Observable<Property[]> {
    return this.http.post<Property[]>(this.api, req);
  }
}
