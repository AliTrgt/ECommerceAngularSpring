import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from '../model/review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private baseUrl = 'http://localhost:8080/v1/review';
  constructor(private http:HttpClient){}

  createReview(review:Review) : Observable<Review>{
      return this.http.post<Review>(`${this.baseUrl}/create`,review);
  }
  
  deleteReview(reviewId:number) : Observable<void>{
      return this.http.delete<void>(`${this.baseUrl}/${reviewId}`);
  }


}
