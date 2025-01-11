import { Component } from '@angular/core';
import { NavigationEnd, RouterOutlet,Router, RouterLink, RouterModule } from '@angular/router';
import { NavbarComponent } from "./navbar/navbar.component";
import { CartComponent } from "./cart/cart.component";
import { HomepageComponent } from "./homepage/homepage.component";
import { CommonModule, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RegisterComponent } from "./register/register.component";
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { UserComponent } from './user/user.component';
import { ProductComponent } from './product/product.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, RouterModule, FormsModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'E-commerce';
  isLoginPage = false;
  isRegisterPage = false;
  
  constructor(private router:Router){
   this.router.events.subscribe( (event) => {
      if(event instanceof NavigationEnd){
          this.isLoginPage = event.url === "/login";
          this.isRegisterPage = event.url ==="/register";
      }
   } )
  }

}
