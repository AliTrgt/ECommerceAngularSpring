import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CartItem } from '../model/cartItem';
import { HttpClient } from '@angular/common/http';
import { Cart } from '../model/cart';

@Injectable({
  providedIn: 'root'
})
export class CartItemService {
  private baseUrl = "http://localhost:8080/v1/cartItem"
  constructor(private http:HttpClient) { }

  getAllCartItem() : Observable<CartItem[]>{
      return this.http.get<CartItem[]>(`${this.baseUrl}`);
  }

  getCartItemById(id:number) : Observable<CartItem>{
      return this.http.get<CartItem>(`${this.baseUrl}/${id}`);
  }

  deleteCartItem(id:number) : Observable<void>{
      return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  updateCartItem(id:number,cartItem:CartItem) :  Observable<CartItem>{
      return this.http.put<CartItem>(`${this.baseUrl}/update/${id}`,cartItem);
  }


}
