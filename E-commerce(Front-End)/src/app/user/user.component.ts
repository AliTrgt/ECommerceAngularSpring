import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthServiceService } from '../services/security_and_guard/auth.service';
import { User } from '../model/user';
import { CommonModule } from '@angular/common';
import { UserService } from '../services/user.service';
import { FormsModule, NgModel } from '@angular/forms';
import { ActivatedRoute, Route, RouterModule } from '@angular/router';
import { Order } from '../model/order';
import { OrderItem } from '../model/orderItem';
import { response } from 'express';


@Component({
  selector: 'app-user',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {
  isRoutingUser: boolean = false;
  showSaveButton = false;
  isEditing = false;
  userId!: number;
  constructor(private userService: UserService, private authService: AuthServiceService, private route: ActivatedRoute) { }
  ngOnInit(): void {
    this.route.paramMap.subscribe(param => {
      this.userId = Number(param.get('id'));
      console.log(this.userId);
      if (this.userId) {
        this.showRoutingUser();
      } else {
        this.getUserData();
      }
    });
  }

  user: User = {
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
    balance: 0,
    authorities: [],
    reviewList: [],
    orderList: [],
  };
  order: Order[] = [];
  orderItem: OrderItem[] = [];
  userTemp: User = { ...this.user };

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

  updateCartInLocalStorage() {
    localStorage.setItem('userData', JSON.stringify(this.user));
    const updatedUserData = localStorage.getItem('userData');
    if (updatedUserData) {
      this.user = JSON.parse(updatedUserData);
    }
  }

  showRoutingUser() {
    this.userService.getUserById(this.userId).subscribe(response => {
      this.isRoutingUser = true;
      this.user = response;
      this.updateCartInLocalStorage();
      console.log(response);
    })
  }
  sendEmail(){
      this.userService.getUserById(this.userId).subscribe(response => {
          this.user = response;
      })
        const email = this.user.email;
        
        const mailto = `mailto:${email}`;

        window.location.href = mailto;
  }

  saveChanges() {
    if (this.user.id !== undefined) { // ID'nin tanımlı olup olmadığını kontrol et
      this.userService.updateUser(this.user.id, this.userTemp).subscribe(
        (data) => {
          this.user = data;
          this.userTemp = { ...this.user };
          this.isEditing = false;
          this.getUserData();
        },
        (error) => {
          console.log("Update failed: " + error);
        }
      );
    } else {
      console.error('User ID is missing. Cannot update the user.'); // Hata mesajı
    }
  }

  logOut() {
    this.authService.logout();
  }

  startEditing() {
    this.userTemp = { ...this.user };
    this.isEditing = true;
  }

  cancelEditing() {
    this.isEditing = false;
  }







}
