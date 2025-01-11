package com.example.E_commerce.dto;

import com.example.E_commerce.model.Cart;
import lombok.Data;

@Data
public class CartItemDto {

    private int id;
    private int quantity;
    private Cart cart;

}
