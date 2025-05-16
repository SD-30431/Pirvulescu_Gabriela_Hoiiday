// src/app/app.config.ts
import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideClientHydration }                from '@angular/platform-browser';
import { provideServerRendering }                from '@angular/platform-server';
import { provideServerRouting }                  from '@angular/ssr';
import { provideRouter }                         from '@angular/router';
import { routes }                                from './app.routes';          // ← import your routes
import { serverRoutes } from './app.routes.server';
import { provideHttpClient } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(),        // ← REGISTER HttpClient
    provideRouter(routes),
    provideServerRendering(),
    provideServerRouting(serverRoutes),
    provideClientHydration()
  ]
};