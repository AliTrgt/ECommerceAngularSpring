import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../services/user.service';
import { User } from '../model/user';
import { Router } from '@angular/router';
import { Role } from '../model/role';
import { error } from 'console';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, RouterModule, FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private router:Router,private userService: UserService) {}
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

  onRegister() {
    this.userService.createUser(this.user).subscribe(
       (response) => {
        console.log("Kullanıcı başarıyla oluşturuldu:", response);
          this.router.navigate(['/homepage']);
      },
      (error) => {
        console.log(this.user);
        console.error("Kullanıcı oluşturulurken hata:", error);
      }
    );
  }

  onRoleSelect(name:string){
      this.user.authorities = [{
        role: name,
        id: 0
      }];
  }
}
