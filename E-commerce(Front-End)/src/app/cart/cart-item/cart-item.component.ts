import { CommonModule, NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { response } from 'express';
import { AuthServiceService } from '../../services/security_and_guard/auth.service';
import { User } from '../../model/user';
import { CartItem } from '../../model/cartItem';
import { CartItemService } from '../../services/cart-item.service';
import { UserComponent } from '../../user/user.component';
import { Cart } from '../../model/cart';

@Component({
  selector: 'app-cart-item',
  standalone: true,
  imports: [FormsModule, CommonModule,RouterLink,RouterModule],
  templateUrl: './cart-item.component.html',
  styleUrl: './cart-item.component.css'
})

export class CartItemComponent {

  constructor(private productService: ProductService, private authService: AuthServiceService, private cartItemService: CartItemService) { }

  ngOnInit() {
    const data = localStorage.getItem('userData');
    if (data) {
      this.recentUser = JSON.parse(data);
      this.getCartItems();
      this.getTotalPrice();
      this.checkTheTotalPriceBeforePurchase();
    }
    else {
      this.getRecentUser();
    }
  }
  recentUser: User = {
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
  totalPrice: number = 0;
  recentCartItems: CartItem[] = [];
  balanceInsufficient: boolean = false; 


  checkTheTotalPriceBeforePurchase() {
    const totalAmount = this.getTotalPrice();  
    if (totalAmount > this.recentUser.balance) { 
      this.balanceInsufficient = true;
      setTimeout(() => {
        this.balanceInsufficient = false; 
      }, 3000);
    } else {
      this.balanceInsufficient = false;  
    }
  }
  

  getTotalPrice(): number {
    this.totalPrice = 0;

    for (let i = 0; i < this.recentCartItems.length; i++) {
      this.totalPrice += this.recentCartItems[i].product?.price! * this.recentCartItems[i].quantity;
    }

    return this.totalPrice;
  }
  getCartItems() {
    this.authService.getUserInfo().subscribe(response => {
      this.recentUser = response;
      if (this.recentUser.cart) {
        this.recentCartItems = this.recentUser.cart.cartItems;
      }
    })
  }

  getRecentUser() {
    this.authService.getUserInfo().subscribe(response => {
      this.recentUser = response;
    })
  }

  removeCartItem(id: number) {
    this.cartItemService.deleteCartItem(id).subscribe(response => {
      this.recentCartItems = this.recentCartItems.filter(item => item.id !== id);
    });
  }

  updateQuantity(itemId: number, cartItem: CartItem) {
    const updatedCartItem = { id: itemId, quantity: cartItem.quantity };

    this.cartItemService.updateCartItem(itemId, updatedCartItem).subscribe(response => {
      console.log(itemId);
      console.log(cartItem.product);
      console.log(response);
      this.checkTheTotalPriceBeforePurchase();
    })
    
  }

  onQuantityBlur(cartItem: CartItem) {
    const product = cartItem.product;
  
    if (product && cartItem.quantity > product.stock) {
      cartItem.quantity = product.stock;
    }
  
    if (cartItem.quantity < 1) {
      cartItem.quantity = 1;  
    }
  

    const updatedCartItem = { id: cartItem.id, quantity: cartItem.quantity };
    this.cartItemService.updateCartItem(cartItem.id, updatedCartItem).subscribe(response => {
      console.log('Cart item updated:', response);
      this.checkTheTotalPriceBeforePurchase();
    });
  }
   
  


  closeAlertBox() {
    this.balanceInsufficient = false;  
  }

}
