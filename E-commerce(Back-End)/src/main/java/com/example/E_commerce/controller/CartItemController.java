package com.example.E_commerce.controller;


import com.example.E_commerce.dto.CartItemDto;
import com.example.E_commerce.model.CartItem;
import com.example.E_commerce.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cartItem")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getAllCartItem() {
        List<CartItemDto> cartItemDto = cartItemService.getAllCartItem();
        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItemDto> findCartItemById(@PathVariable int cartItemId) {
        CartItemDto cartItemDto = cartItemService.findById(cartItemId);
        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CartItemDto> createCartItem(@RequestBody CartItem cart) {
        CartItemDto cartItemDto = cartItemService.createCartItem(cart);
        return new ResponseEntity<>(cartItemDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable int cartItemId, @RequestBody CartItem cartItem) {
        CartItemDto cartItemDto = cartItemService.updateCartItem(cartItemId, cartItem);
        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }



    @DeleteMapping("/{cartItemId}")
    public void deleteCartItem(@PathVariable(name = "cartItemId") int cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }




}
