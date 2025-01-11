import { Component } from '@angular/core';
import { User } from '../../model/user';
import { OrderItem } from '../../model/orderItem';
import { Order } from '../../model/order';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthServiceService } from '../../services/security_and_guard/auth.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-order-item',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterLink],
  templateUrl: './order-item.component.html',
  styleUrl: './order-item.component.css'
})
export class OrderItemComponent {

    constructor(private authService:AuthServiceService){}
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
    order:Order[] = [];
    orderItem:OrderItem[] = [];
   

  ngOnInit(){
      this.getUserData();
  }


  getUserData() {
    this.authService.getUserInfo().subscribe(response => {
        this.user = response;
        this.updateCartInLocalStorage();
        this.order = this.user.orderList;   
    }, error => {
        console.log("error : ", error);
    }
    )
}

getOrderStatus(orderDate: Date): string {
  const currentDate = new Date();
  const deliveryDate = new Date(orderDate);
  deliveryDate.setDate(deliveryDate.getDate() + 1); // Siparişten 1 gün sonra

  const receivedDate = new Date(orderDate);
  receivedDate.setDate(receivedDate.getDate() + 2); // Kargodan 1 gün sonra

  if (currentDate >= receivedDate) {
    return "Delivered";
  } else if (currentDate >= deliveryDate) {
    return "Shipped";
  } else {
    return "Will be Delivered";
  }
}


  updateCartInLocalStorage() {
    localStorage.setItem('userData', JSON.stringify(this.user));
    const updatedUserData = localStorage.getItem('userData');
    if (updatedUserData) {
        this.user = JSON.parse(updatedUserData);  
    }
}
  
reloadPage(){
    window.location.reload();
}
  
  
 





}
