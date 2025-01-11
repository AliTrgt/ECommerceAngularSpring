import { Category } from "./category"
import { Review } from "./review";
import { User } from "./user";

export interface Product {
     id: number;
     name: string;
     description: string;
     price: number;
     stock: number;
     category?: Category | null;
     reviewList: Review[];
     imageUrl:String;

}
