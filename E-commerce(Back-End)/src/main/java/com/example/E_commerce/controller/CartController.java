package com.example.E_commerce.controller;


import com.example.E_commerce.dto.CartDto;
import com.example.E_commerce.model.Cart;
import com.example.E_commerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCustomerCart() {
        List<CartDto> cartList = cartService.getAllCart();
        return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> findCartById(@PathVariable int cartId) {
        CartDto cart = cartService.findById(cartId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


    @GetMapping("/getCalculate/{cartId}")
    public Double calculateToPrice(@PathVariable int cartId){
        return cartService.calculateToPrice(cartId);
    }


    @GetMapping("/findCartByUser")
    public ResponseEntity<CartDto> findCartByUser(@RequestParam int userId){
        CartDto cart = cartService.findCartByUser(userId);
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

}
