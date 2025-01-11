import { Product } from "./product";
import { Cart } from "./cart";

export interface CartItem {

     id: number;
     quantity: number;
     cart?: Cart;
     product?: Product;


}