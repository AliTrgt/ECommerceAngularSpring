import { Component } from '@angular/core';
import { CartItemComponent } from "./cart-item/cart-item.component";
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProductService } from '../services/product.service';
import { AuthServiceService } from '../services/security_and_guard/auth.service';
import { CartItemService } from '../services/cart-item.service';
import { User } from '../model/user';
import { CartItem } from '../model/cartItem';
import { CartService } from '../services/cart.service';
import { response } from 'express';
import { error } from 'console';
import { Cart } from '../model/cart';
import { OrderService } from '../services/order.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CartItemComponent, FormsModule, CommonModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  constructor(private cartService: CartService, private authService: AuthServiceService, private orderService: OrderService, private router: Router) { }
  totalPrice: number = 0;
  recentUser: User = { username: '', password: '', firstName: '', lastName: '', email: '', balance: 0, authorities: [], reviewList: [], orderList: [] }
  recentCartItems: CartItem[] = [];
  canAfford: boolean = true;

  ngOnInit() {
    const data = localStorage.getItem('userData');
    if (data) {
      this.recentUser = JSON.parse(data);
      this.calculateTotalPrice();
      this.getCartItems();
      this.isCustomerAffordThisProduct();
    }
    else {
      this.getRecentUser();
    }
  }

  getRecentUser() {
    this.authService.getUserInfo().subscribe(response => {
      this.recentUser = response;
    })
  }

  getCartItems() {
    this.authService.getUserInfo().subscribe(response => {
      this.recentUser = response;
      if (this.recentUser.cart) {
        this.recentCartItems = this.recentUser.cart.cartItems;
      }
      console.log(this.recentCartItems);
    })
  }

  completeTheProcess() {
    this.isCustomerAffordThisProduct();
    if (this.recentUser.cart?.id) {
      this.orderService.placeOrder(this.recentUser.cart?.id, "CASH").subscribe(response => {
        console.log(response);
        this.router.navigate(['/orderpage'])
      })
    }
  }

  calculateTotalPrice() {
    if (this.recentUser.id !== undefined) {
      this.cartService.CalculateToTotalCart(this.recentUser.id).subscribe(response => {
        this.totalPrice = response;
        console.log("toplamTutar : ", this.totalPrice);

      }, error => {
        console.log("Can not Calculate to Cart Amount", error);
      }
      )
    }

  }

  isCustomerAffordThisProduct() {
      
    if (this.recentUser.balance < this.totalPrice) {
      this.canAfford = false;
      setTimeout(() => {
        this.canAfford = true;
      }, 3000);
    }
   else {this.canAfford = true;} 
  }

  closeAlertBox() {
    this.canAfford = true;
  }

}
