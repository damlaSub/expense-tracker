import { ApplicationConfig, APP_INITIALIZER } from '@angular/core';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HttpInterceptorService } from './services/http.intercepter';
import { HttpService } from './services/http.service';
import { LocalizationService } from './services/localization.service';

// Initialize LocalizationService before app bootstraps
export function initializeLocalization(localizationService: LocalizationService) {
  return () => localizationService.loadLanguage('en'); 
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptorsFromDi()),
    HttpService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    },
    provideRouter(routes),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeLocalization,
      deps: [LocalizationService], // Makes sure LocalizationService is available for initialization
      multi: true, 
    },
  ],
};
