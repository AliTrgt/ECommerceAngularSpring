import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { response } from 'express';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { User } from '../model/user';
import { error } from 'node:console';
import { AuthServiceService } from '../services/security_and_guard/auth.service';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [RouterLink, RouterModule, FormsModule, CommonModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {

    constructor(private authService:AuthServiceService){}
    
    isLoggedIn(): boolean {
      return this.authService.isLoggedIn();
    }


}
