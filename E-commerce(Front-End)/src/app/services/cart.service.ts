import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private baseUrl = "http://localhost:8080/v1/cart";
  constructor(private http:HttpClient) {}


  CalculateToTotalCart(id:number): Observable<number>{
      return this.http.get<number>(`${this.baseUrl}/getCalculate/${id}`);
  }

  

}
