import { Pipe, PipeTransform } from '@angular/core';
import { Product } from '../model/product';

@Pipe({
  name: 'search',
  standalone: true
})
export class SearchPipe implements PipeTransform {

  transform(products:Product[],searchText:string): Product[] {
      if(!searchText || searchText.trim() === ''){
          return products;
      }
      const lowerSearchText = searchText.toLowerCase();
      return products.filter(product => 
          product.name.toLowerCase().includes(lowerSearchText)
      );
  }

}
