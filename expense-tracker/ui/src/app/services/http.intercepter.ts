import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Add headers globally (e.g., Auth Token, Content-Type)
    const token = localStorage.getItem('token'); 
    const clonedReq = req.clone({
      setHeaders: {
        Authorization: token ? `Bearer ${token}`: '',  //  manage token storage and retrieval 
        'Content-Type': 'application/json',
      },
    });

    return next.handle(clonedReq);
  }
}
