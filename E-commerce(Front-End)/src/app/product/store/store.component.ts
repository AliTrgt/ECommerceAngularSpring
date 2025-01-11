import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Router } from 'express';
import { Product } from '../../model/product';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-store',
  standalone: true,
  imports: [RouterLink,CommonModule],
  templateUrl: './store.component.html',
  styleUrl: './store.component.css'
})
export class StoreComponent {
    products:Product[] = [];

   





}
