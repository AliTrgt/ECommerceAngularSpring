import { Product } from "./product";
import { User } from "./user";

export interface Review {
     id: number;
     rating: number;
     comment: string;
     user:User | null;
     product: Product | null;

    
}