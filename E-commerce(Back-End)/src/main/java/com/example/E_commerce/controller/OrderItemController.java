package com.example.E_commerce.controller;

import com.example.E_commerce.dto.OrderItemDto;
import com.example.E_commerce.model.OrderItem;
import com.example.E_commerce.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orderItem")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItemDto>> getAllOrderItem() {
        List<OrderItemDto> orderItemList = orderItemService.getAllOrderItem();
        return new ResponseEntity<>(orderItemList, HttpStatus.OK);
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItemDto> findOrderedItemById(@PathVariable int orderItemId) {
        OrderItemDto orderItemDto = orderItemService.findById(orderItemId);
        return new ResponseEntity<>(orderItemDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<OrderItemDto> createOrderItem(@RequestBody OrderItem orderItem) {
        OrderItemDto orderItemDto = orderItemService.createOrderItem(orderItem);
        return new ResponseEntity<>(orderItemDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{orderItemId}")
    public ResponseEntity<OrderItemDto> updateOrderItem(@PathVariable int orderItemId, @RequestBody OrderItem orderItem) {
        OrderItemDto orderItemDto = orderItemService.updateOrderItem(orderItemId, orderItem);
        return new ResponseEntity<>(orderItemDto, HttpStatus.OK);
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable int orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
        return new ResponseEntity<>("OrderItem successfully deleted ", HttpStatus.OK);
    }


}
