import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthServiceService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private authService:AuthServiceService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      const accessToken = this.authService.getToken();
      if(accessToken){
          req = req.clone({
              setHeaders: {
                Authorization: `Bearer ${accessToken}`,
              },
          });
      }
        return next.handle(req);
  }
}
