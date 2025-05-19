// src/app/services/auth.service.ts
import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { HttpClient }                     from '@angular/common/http';
import { isPlatformBrowser }              from '@angular/common';
import { BehaviorSubject, Observable, of }from 'rxjs';
import { tap, map, catchError }           from 'rxjs/operators';
import { Router }                         from '@angular/router';

export interface LoginSuccessPayload {
  success: boolean;
  message: string;
  token: string;
  userId: number;
  firstName: string;
  lastName: string; 
  email: string;
  role: 'ADMIN' | 'CLIENT';
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API = '/api/auth';
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY  = 'current_user';

  private user$ = new BehaviorSubject<LoginSuccessPayload|null>(null);
  public  currentUser$ = this.user$.asObservable();
  private platformId = inject(PLATFORM_ID);

  constructor(private http: HttpClient, private router: Router) {
    this.attemptAutoLogin();
  }

  attemptAutoLogin() {
    if (!isPlatformBrowser(this.platformId)) return;
    const token = localStorage.getItem(this.TOKEN_KEY);
    const user  = localStorage.getItem(this.USER_KEY);
    if (token && user) {
      this.user$.next(JSON.parse(user));
    }
  }

  attemptLogin(email: string, password: string): Observable<LoginSuccessPayload> {
    return this.http.post<LoginSuccessPayload>(
      `${this.API}/login`,
      { email, password }
    ).pipe(
      tap(res => {
        if (res.success && res.token) {
          if (isPlatformBrowser(this.platformId)) {
            localStorage.setItem(this.TOKEN_KEY, res.token);
            localStorage.setItem(this.USER_KEY, JSON.stringify(res));
          }
          this.user$.next(res);
        }
      })
    );
  }

  logout() {
    if (!this.user$.value) return of(null);
    return this.http.post(`${this.API}/logout`, { userId: this.user$.value.userId })
      .pipe(tap(() => {
        if (isPlatformBrowser(this.platformId)) {
          localStorage.removeItem(this.TOKEN_KEY);
          localStorage.removeItem(this.USER_KEY);
        }
        this.user$.next(null);
        this.router.navigate(['/']);
      }));
  }

  isAuthenticated(): Observable<boolean> {
    return this.currentUser$.pipe(map(u => !!u && !!u.token));
  }

  getSnapshotCurrentUser() {
    return this.user$.value;
  }
  private saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getAuthToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }
}
