// src/app/security/role.guard.ts
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map, tap } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router      = inject(Router);
  const requiredRole = route.data['role'] as string;

  return authService.currentUser$.pipe(
    map(user => !!user && user.role === requiredRole),
    tap(hasAccess => {
      if (!hasAccess) {
        router.navigate(['/']);
      }
    })
  );
};
