import { Component, resolveForwardRef } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../model/product';
import { Router } from '@angular/router';
import { error } from 'console';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterModule } from '@angular/router';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../model/category';
import { strict } from 'assert';
import { AuthServiceService } from '../../services/security_and_guard/auth.service';
import { User } from '../../model/user';
import e, { response } from 'express';
import { HttpClient } from '@angular/common/http';
import { SearchPipe } from '../../pipe/search.pipe';
@Component({
  selector: 'app-create-product',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink, RouterModule],
  templateUrl: './create-product.component.html',
  styleUrl: './create-product.component.css'
})
export class CreateProductComponent {
  preview: string | ArrayBuffer | null = null;
  selectedFile: File | null = null;

  constructor(private router: Router, private productService: ProductService, private categoryService: CategoryService, private authService: AuthServiceService,private http:HttpClient) { }
  ngOnInit() {
    this.getCategoryData(); 
  }
  
  product: Product = { id: 0, name: '', description: '', price: 0, stock: 0, reviewList: [], imageUrl: '',}
  user: User = {username: '',password: '',firstName: '',lastName: '',email: '',balance: 0,authorities: [],reviewList: [],orderList: []}
  category: Category[] = [];

  getCategoryData() {
    this.categoryService.getAllCategory().subscribe(response => {
      this.category = response;
    })
  }

  createProduct() {
    this.productService.createProduct(this.product).subscribe(response => {
        console.log(response);
        this.router.navigate([`/productMainPage/${response.id}`]);
    },error => {
        console.log(error);
    })
  }


}
