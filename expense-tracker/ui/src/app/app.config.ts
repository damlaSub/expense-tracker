import { ApplicationConfig } from '@angular/core';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HttpInterceptorService } from './services/http.intercepter';
import { HttpService } from './services/http.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptorsFromDi()), // Adds HttpClient with interceptors
    HttpService, // Register the HttpService
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService, // Register HttpInterceptorService
      multi: true, // Ensures that multiple interceptors can be used
    },
    provideRouter(routes), // Register routing
  ],
};
