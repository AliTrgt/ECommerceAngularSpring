package com.example.E_commerce.controller;


import com.example.E_commerce.dto.OrderDto;
import com.example.E_commerce.mapper.OrderMapper;
import com.example.E_commerce.model.Order;
import com.example.E_commerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrder() {
        List<OrderDto> orderList = orderService.getAllOrder();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable int orderId) {
        OrderDto orderDto = orderService.findById(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody Order order) {
        OrderDto orderDto = orderService.createOrder(order);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable int orderId, @RequestBody Order order) {
        OrderDto orderDto = orderService.updateOrder(orderId, order);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Order successfully deleted ", HttpStatus.OK);
    }

    @PostMapping("/placeOrder/{cartId}")
    public OrderDto placeOrder(@PathVariable int cartId,@RequestParam String paymentType){
        return orderService.placeOrder(cartId,paymentType);
    }


}
