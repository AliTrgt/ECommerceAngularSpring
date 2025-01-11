package com.example.E_commerce.dto;


import lombok.Data;

import java.util.List;

@Data
public class CartDto {

    private int id;
    private int cartId;
    private List<CartItemDto> cartItems;
}
