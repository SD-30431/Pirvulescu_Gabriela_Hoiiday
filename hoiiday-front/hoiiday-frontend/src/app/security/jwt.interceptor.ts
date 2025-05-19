// src/app/security/jwt.interceptor.ts
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser }       from '@angular/common';
import { HttpInterceptorFn }       from '@angular/common/http';
import { AuthService } from '../services/auth.service';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);
  const token = inject(AuthService).getAuthToken(); 
  if (token) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }
  return next(req);
};
