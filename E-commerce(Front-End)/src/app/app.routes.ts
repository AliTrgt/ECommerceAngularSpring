import { Routes } from '@angular/router';
import { AboutUsComponent } from './about-us/about-us.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuardService } from './services/security_and_guard/auth-guard.service';
import { ProductComponent } from './product/product.component';
import { UserComponent } from './user/user.component';
import { CartComponent } from './cart/cart.component';
import { CreateProductComponent } from './product/create-product/create-product.component';
import { ProductPageComponent } from './product/product-page/product-page.component';
import { StoreComponent } from './product/store/store.component';
import { OrderComponent } from './order/order.component';
import { BalanceComponent } from './user/balance/balance.component';

export const routes: Routes = [
     {path:"about",component:AboutUsComponent},
     {path:"homepage",component:HomepageComponent},
     {path:"loginpage",component:LoginComponent},
     {path:"registerpage",component:RegisterComponent},
     {path:"productpage",component:ProductComponent},
     {path:"accountpage/:id",component:UserComponent},
     {path:"accountpage",component:UserComponent,pathMatch:'full'},
     {path:"cartpage",component:CartComponent},
     {path:"createproductpage",component:CreateProductComponent},
     {path:"productMainPage/:id",component:ProductPageComponent},
     {path:"store",component:StoreComponent},
     {path:"orderpage",component:OrderComponent,canActivate:[AuthGuardService]},
     {path:"balancepage",component:BalanceComponent},
     {path:"",redirectTo:"homepage",pathMatch:"full"}
];
