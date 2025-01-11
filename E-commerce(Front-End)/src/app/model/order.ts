import { User } from "./user";
import { OrderItem } from "./orderItem";

export interface Order {

     id: number;
     orderDate: Date;
     totalAmount: number;
     user: User;
     orderItemsList: OrderItem[];
     paymentType: string;

     
}