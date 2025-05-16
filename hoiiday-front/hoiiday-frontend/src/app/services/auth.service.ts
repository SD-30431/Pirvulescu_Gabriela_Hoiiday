// src/app/services/auth.service.ts
import { Injectable }      from '@angular/core';
import { HttpClient }      from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap }             from 'rxjs/operators';

export interface LoginSuccessPayload {
  userId:    number;
  firstName: string;
  lastName:  string;
  role:      'ADMIN'|'CLIENT'|string;
  token?:    string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API = 'http://localhost:8080/api/auth';

  private user$ = new BehaviorSubject<LoginSuccessPayload|null>(null);
  public  currentUser$ = this.user$.asObservable();

  constructor(private http: HttpClient) {
  }

  attemptLogin(email: string, password: string): Observable<LoginSuccessPayload> {
    return this.http
      .post<LoginSuccessPayload>(`${this.API}/login`, { email, password })
      .pipe(
        tap(u => {
          if (u.token) localStorage.setItem('authToken', u.token);
          this.user$.next(u);
        })
      );
  }

  logout() {
    localStorage.removeItem('authToken');
    this.user$.next(null);
  }

  getCurrentUserId(): number | null {
    return this.user$.value?.userId ?? null;
  }

  isAuthenticated(): boolean {
    return this.user$.value != null;
  }
}
