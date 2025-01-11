package com.example.E_commerce.dto;


import com.example.E_commerce.model.Order;
import com.example.E_commerce.model.Product;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderItemDto {

    private int id;
    private int quantity;
    private BigDecimal price;
    private Order order;
    private Product product;

}
