import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../model/product';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = "http://localhost:8080/v1/product"

  constructor(private http: HttpClient) { }

  getAllProduct(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/${id}`)
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.baseUrl}/create`, product);
  }

  updateProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.baseUrl}/update/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  addProductToCart(productId:number,cartId:number,quantity:number) : Observable<void>{
    const params = new HttpParams()
    .set('productId',productId.toString())
    .set('cartId',cartId.toString())
    .set('quantity',quantity.toString())

    return this.http.post<void>(`${this.baseUrl}/addProductToCart`,null,{params});

  }

  uploadPhoto(productId:number,file:File) : Observable<string> {
    const formData = new FormData();
    formData.append('file',file,file.name);

    return this.http.post<string>(`http://localhost:8080/v1/photos/upload/${productId}`, formData);

  }

  getByPriceRange(low:number,high:number) : Observable<Product[]>{
        const params = new HttpParams()
        .set('low',low.toString())
        .set('high',high.toString())

      return this.http.get<Product[]>(`${this.baseUrl}/filterByPrice`,{params})
  }

}
