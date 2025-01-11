import { Pipe, PipeTransform } from '@angular/core';
import { Product } from '../model/product';

@Pipe({
  name: 'categoryFilter',
  standalone: true
})
export class CategoryFilterPipe implements PipeTransform {

  transform(products:Product[],selectedCategory:string): Product[] {
    if(!products || !selectedCategory){
        return products;
    }
    return products.filter(product => product.category?.name == selectedCategory);
  }

}
