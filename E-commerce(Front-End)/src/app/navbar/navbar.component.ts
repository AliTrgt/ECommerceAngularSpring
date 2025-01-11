import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterModule } from '@angular/router';
import { AuthServiceService } from '../services/security_and_guard/auth.service';
import { User } from '../model/user';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterModule, FormsModule, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {

  role: string[] = [];
  isSeller = false;
  constructor(private authService: AuthServiceService) {}
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
  ngOnInit() {
      this.getUserData();
  }

 
  getUserData() {
    this.authService.getUserInfo().subscribe(response => {
      this.role = response.authorities.map(auth => auth.role);
      if(this.role.includes('ROLE_SELLER')){ 
            this.isSeller = true;
      } 
      else this.isSeller = false;
    }, error => {
      console.log('Error fetching user data:', error);
    });
  }


  isLoggedIn(): boolean {
    return this.authService.isLoggedIn(); 
  }

  logOut(): void {
    this.authService.logout();
  }



}
