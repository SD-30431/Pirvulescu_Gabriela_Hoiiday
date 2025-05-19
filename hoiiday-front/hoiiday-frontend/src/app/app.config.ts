// src/app/app.config.ts

import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideClientHydration }             from '@angular/platform-browser';
import { provideServerRendering }            from '@angular/platform-server';
import { provideServerRouting }              from '@angular/ssr';
import { provideRouter }                     from '@angular/router';
import {
  provideHttpClient,
  withFetch,
  withInterceptors
} from '@angular/common/http';

import { routes }       from './app.routes';
import { serverRoutes } from './app.routes.server';
import { DarkModeService } from './services/dark-mode.service';
import { jwtInterceptor }  from './security/jwt.interceptor';

export function initDarkModeFactory(darkModeService: DarkModeService) {
  return () => Promise.resolve();
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(
      withFetch(),                       // SSR → Fetch backend
      withInterceptors([jwtInterceptor]) // our guarded interceptor
    ),

    provideRouter(routes),
    provideServerRendering(),
    provideServerRouting(serverRoutes),
    provideClientHydration(),

    {
      provide: APP_INITIALIZER,
      useFactory: initDarkModeFactory,
      deps: [DarkModeService],
      multi: true
    }
  ]
};
