import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { basename } from 'path';
import { BehaviorSubject, Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode'; // jwt-decode kütüphanesini kullanarak
import { User } from '../../model/user';


@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  private baseUrl = "http://localhost:8080/v1/user"
  constructor(private http: HttpClient, private router: Router) { }
  user:User = {
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
    balance: 0,
    authorities: [],
    reviewList: [],
    orderList: []
  }
  role:String[] = [];

  login(username: string, password: string): Observable<{ accessToken: string, refreshToken: string }> {
    return this.http.post<{ accessToken: string, refreshToken: string }>(`${this.baseUrl}/login`, { username, password });
  }
  saveToken(accessToken: string, refreshToken: string) {
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
  }
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
  getToken(): string | null {
    return localStorage.getItem('accessToken');
  }
  logout(): void {
    if (localStorage.getItem('accessToken') && localStorage.getItem('refreshToken')) {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      localStorage.removeItem('userData');
      this.router.navigate(['/loginpage']);
    }

  }

  getUserAuthorities() :User {
      const data = localStorage.getItem('userData');
      if(data){
          this.user = JSON.parse(data);
          this.role =  this.user.authorities.map(x => x.role);
      }
      return this.user;
  }

  isSeller():boolean{
      this.getUserAuthorities();
      if(this.role.includes('ROLE_SELLER')){
          return true;
      }
      return false;
  }


  getUserInfo(): Observable<User> {
    const token = localStorage.getItem('accessToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`

    })
    return this.http.get<User>("http://localhost:8080/v1/user/getUserInfo", { headers });
  }




}
