import { Product } from "./product";
import { Order } from "./order";

export interface OrderItem {

     id: number;
     quantity: number;
     price: number;
     order: Order | null;
     product: Product | null;

    
}