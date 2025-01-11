package com.example.E_commerce.service;


import com.example.E_commerce.dto.CartDto;
import com.example.E_commerce.exception.IdNotFoundException;
import com.example.E_commerce.mapper.CartMapper;
import com.example.E_commerce.model.Cart;

import com.example.E_commerce.model.CartItem;

import com.example.E_commerce.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    public List<CartDto> getAllCart() {
        List<Cart> cartList = cartRepository.findAll();
        return cartList.stream().map(cartMapper::toDto).collect(Collectors.toList());
    }

    public CartDto findById(int cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IdNotFoundException(cartId));
        return cartMapper.toDto(cart);
    }

    public CartDto findCartByUser(int userId){
            Cart cart = cartRepository.findCartByUserId(userId);
            return cartMapper.toDto(cart);
    }

    public double calculateToPrice(int cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IdNotFoundException(cartId));
        double price = 0,amount = 0;
        double lastQuantity = 0;
        double totalAmount = 0;
        for(CartItem tempCartItem:cart.getCartItems()){
            price = tempCartItem.getProduct().getPrice();
            lastQuantity = tempCartItem.getQuantity();
            amount = price * lastQuantity;
            totalAmount += amount;
        }
        return totalAmount;
    }


}
