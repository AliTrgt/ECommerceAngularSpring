import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from '../model/order';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseUrl = "http://localhost:8080/v1/order"
  constructor(private http:HttpClient) { }

  placeOrder(cartId:number,paymentType:string) : Observable<Order>{
      const params = new HttpParams()
        .set('paymentType',paymentType.toString());
    return this.http.post<Order>(`${this.baseUrl}/placeOrder/${cartId}`,null,{params})
  }


}
