import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rules } from '../model/rules.model';

@Injectable({
  providedIn: 'root',
})
export class RulesService {
  private apiUrl = '/api/rules'; // Adjust if your backend URL is different

  constructor(private http: HttpClient) {}

  getAllRules(): Observable<Rules[]> {
    return this.http.get<Rules[]>(this.apiUrl);
  }

  getRuleById(id: number): Observable<Rules> {
    return this.http.get<Rules>(`${this.apiUrl}/${id}`);
  }

  createRule(rule: Rules): Observable<Rules> {
    return this.http.post<Rules>(this.apiUrl, rule);
  }

  updateRule(id: number, rule: Rules): Observable<Rules> {
    return this.http.put<Rules>(`${this.apiUrl}/${id}`, rule);
  }

  deleteRule(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}