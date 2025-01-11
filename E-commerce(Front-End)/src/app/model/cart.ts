import { User } from "./user";
import { CartItem } from "./cartItem";

export interface Cart {

     id?: number;
     user?: User;
     cartItems: CartItem[];

     
}