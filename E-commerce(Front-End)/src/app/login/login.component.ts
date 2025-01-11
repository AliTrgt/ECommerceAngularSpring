import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';

import { FormsModule } from '@angular/forms';
import { AuthServiceService } from '../services/security_and_guard/auth.service';
import { User } from '../model/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, RouterModule, FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  errorMessage = ''; 
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
  constructor(private authService:AuthServiceService,private router:Router){}

  onLogin(){
      this.authService.login(this.user.username,this.user.password).subscribe((response) => {
            this.authService.saveToken(response.accessToken,response.refreshToken);
            console.log('accessToken'+response.accessToken);
            console.log('accessToken'+response.refreshToken);
            this.router.navigate(['/']).then( () => {
              window.location.reload();
            });
      }, (error) => {
        console.error('Login failed', error);
        this.errorMessage = 'Giriş başarısız oldu. Lütfen kullanıcı adı ve şifreyi kontrol edin.'; // Hata mesajını ayarlayın
      } 
    )
  }


}
