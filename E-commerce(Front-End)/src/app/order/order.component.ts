import { Component } from '@angular/core';
import { OrderItemComponent } from "./order-item/order-item.component";

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [OrderItemComponent],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent {

}
