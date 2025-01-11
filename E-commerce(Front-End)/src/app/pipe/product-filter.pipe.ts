import { Pipe, PipeTransform } from '@angular/core';
import { Product } from '../model/product';

@Pipe({
  name: 'productFilter',
  standalone: true
})
export class ProductFilterPipe implements PipeTransform {

  transform(products:Product[],lowPrice:number,highPrice:number): Product[] {
      if(!lowPrice && !highPrice){
          return products;
      }  
    
    return products.filter(x => x.price <= highPrice && x.price >= lowPrice);
  }

}
