import { Component } from '@angular/core';
import { CategoryService } from '../services/category.service';
import { Category } from '../model/category';
import { error } from 'console';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent {
      
    constructor(private categoryService:CategoryService){}
      categories: Category[] = [];

      ngOnInit(){
            this.getAllCategoryData();
      }

      getAllCategoryData(){
            this.categoryService.getAllCategory().subscribe(data => {
                this.categories = data;
            },error => {
                console.log("Can Not Fetch Category "+error);
            }
          )
      }
        
}
