package com.example.E_commerce.dto;


import com.example.E_commerce.model.OrderItem;
import com.example.E_commerce.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private int id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private int userId;
    private List<OrderItem> orderItems;
    private String paymentType;

}
