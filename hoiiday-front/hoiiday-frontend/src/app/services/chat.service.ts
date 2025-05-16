import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, of } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private readonly API = 'http://localhost:8080/api/chat';

  constructor(private http: HttpClient) {}

  ask(prompt: string): Observable<string> {
    return this.http
      .get(`${this.API}/ask`, {
        params: { prompt },
        responseType: 'text' 
      })
      .pipe(
       
        catchError(err => {
          console.error('Error calling chat API:', err);
          return of(typeof err.error === 'string'
            ? err.error
            : "Sorry, I couldn't process your request. Please try again later."
          );
        })
      );
  }
  
}