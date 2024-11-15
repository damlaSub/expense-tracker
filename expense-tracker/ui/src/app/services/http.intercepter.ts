import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('token'); 
    const clonedReq = req.clone({
      setHeaders: {
        Authorization: token ? `Bearer ${token}` : '', // Add token to request headers
        'Content-Type': 'application/json',
      },
    });

    return next.handle(clonedReq).pipe(
      // Intercept response
      map((response: HttpEvent<any>) => {
        if (response instanceof HttpResponse) {
          this.handleResponse(response); // Handle the response
        }
        return response;
      }),
      catchError((error) => {
        // Handle error if needed
        throw error;
      })
    );
  }

  // Method to handle the response
  private handleResponse(response: HttpResponse<any>): void {
    const data = response.body;
console.log("data :" , data);
    // Check if the response contains the token
    if (data && data.token) {
      this.updateLocalStorage(data);
    }
  }

  // Method to update localStorage
  private updateLocalStorage(data: any): void {
    const { token, refreshToken, firstName } = data;

    // Clear old localStorage values
    localStorage.clear();

    // Store the new values
    localStorage.setItem("token", token);
    localStorage.setItem("refreshToken", refreshToken);
    localStorage.setItem("isAuthenticated", 'true'); 
    localStorage.setItem("userName", firstName);



    console.log("localStorage after update:", {
      token: localStorage.getItem("token"),
      refreshToken: localStorage.getItem("refreshToken"),
      isAuthenticated: localStorage.getItem("isAuthenticated"),
      userName: localStorage.getItem("userName"),
    });
  }
}
