import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../model/category';

@Injectable({
    providedIn: 'root'
})
export class CategoryService {
    private baseUrl = "http://localhost:8080/v1/category"
    constructor(private http: HttpClient) { }

    getAllCategory(): Observable<Category[]> {
        return this.http.get<Category[]>(`${this.baseUrl}`);
    }

    getCategoryById(id: number): Observable<Category> {
        return this.http.get<Category>(`${this.baseUrl}/${id}`);
    }

    createCategory(category: Category): Observable<Category> {
        return this.http.post<Category>(`${this.baseUrl}/create`, category);
    }

    updateCategory(id: number, category: Category): Observable<Category> {
        return this.http.put<Category>(`${this.baseUrl}/update/${id}`, category);
    }

    deleteCategory(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }

}
