import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private baseUrl = "http://localhost:8080/v1/user";
    constructor(private http: HttpClient) { }

    getAllUser(): Observable<User[]> {
        return this.http.get<User[]>(`${this.baseUrl}`);
    }

    getUserById(id: number): Observable<User> {
        return this.http.get<User>(`${this.baseUrl}/${id}`);
    }

    createUser(user:User):Observable<User> {
        return this.http.post<User>(`${this.baseUrl}/create`,user);
    }

    updateUser(id: number, user: User): Observable<User> {
        return this.http.put<User>(`${this.baseUrl}/update/${id}`, user);
    }

    deleteUser(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }

    addBalanceToUser(userId:number,amount:number) : Observable<number>{
        const params = new HttpParams()
        .set('amount',amount.toString());   
        return this.http.post<number>(`${this.baseUrl}/${userId}`,null, {params});
    }

    

}
