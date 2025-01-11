import { Cart } from "./cart";
import { Order } from "./order";
import { Product } from "./product";
import { Review } from "./review";
import { Role } from "./role";

export interface User {
     id?:number;
     username: string;
     password: string;
     firstName: string;
     lastName: string;
     email: string;
     balance: number;
     cart?: Cart;
     authorities: Role[];
     reviewList: Review[];
     orderList: Order[];
     profileImage?: string | null;
   
}