import { Component } from '@angular/core';
import { AuthServiceService } from '../../services/security_and_guard/auth.service';
import { User } from '../../model/user';
import { UserService } from '../../services/user.service';
import { response } from 'express';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-balance',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './balance.component.html',
  styleUrl: './balance.component.css'
})
export class BalanceComponent {
  user: User = {username: '',password: '',firstName: '',lastName: '',email: '',balance: 0,authorities: [],reviewList: [],orderList: []}
  selectedBalance: number | null = null; 
  lastBalance:number | null = null;
  popUp:boolean = false;
  constructor(private authService: AuthServiceService,private userService:UserService) {

  }
  ngOnInit(){
      this.getUserData();    
  }

  addBalanceToUser(amount:number) : void {
    this.selectedBalance = amount;
  }

  confirmBalance(): void {
    if (this.selectedBalance !== null) {
     this.userService.addBalanceToUser(this.user.id ?? 0, this.selectedBalance).subscribe(response => {
          console.log(response);
          this.popUp = true;
          setTimeout(() => {
              this.popUp = false;
          },3000)
          this.user.balance += this.selectedBalance ?? 0;
          setTimeout(() => {
           this.selectedBalance = null;
        },3000)
        })
    }
  }

  closePopUp(){
      this.popUp = false;
  }

  getUserData() {
    this.authService.getUserInfo().subscribe(response => {
      this.user = response;
      this.updateCartInLocalStorage();
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



}
