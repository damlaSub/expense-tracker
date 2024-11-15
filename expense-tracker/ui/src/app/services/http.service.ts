import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',  // Make the service globally available
})
export class HttpService {
  private apiUrl = environment.apiURL  

  constructor(private http: HttpClient) {}

  // POST request
  post<T>(endpoint: string, body: any, headers: HttpHeaders = new HttpHeaders()): Observable<T> {
    return this.http
      .post<T>(`${this.apiUrl}/${endpoint}`, body, { headers })
      .pipe(
        map((response) => response),
        catchError(this.handleError)
      );
  }

  // GET request
  get<T>(endpoint: string, params: HttpParams = new HttpParams(), headers: HttpHeaders = new HttpHeaders()): Observable<T> {
    return this.http
      .get<T>(`${this.apiUrl}/${endpoint}`, { headers, params })
      .pipe(
        map((response) => response),
        catchError(this.handleError)
      );
  }

  // PATCH request
  put<T>(endpoint: string, body: any, headers: HttpHeaders = new HttpHeaders()): Observable<T> {
    return this.http
      .patch<T>(`${this.apiUrl}/${endpoint}`, body, { headers })
      .pipe(
        map((response) => response),
        catchError(this.handleError)
      );
  }

  // DELETE request
  delete<T>(endpoint: string, headers: HttpHeaders = new HttpHeaders()): Observable<T> {
    return this.http
      .delete<T>(`${this.apiUrl}/${endpoint}`, { headers })
      .pipe(
        map((response) => response),
        catchError(this.handleError)
      );
  }

  // Global error handler
  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('A network error occurred:', error.error);
    } else {
      console.error(`Backend returned code ${error.status}, body was:`, error.error);
    }
    return throwError('Something went wrong; please try again later.');
  }
}
