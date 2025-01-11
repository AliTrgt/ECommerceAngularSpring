package com.example.E_commerce.dto;


import com.example.E_commerce.model.Product;
import com.example.E_commerce.model.User;
import lombok.Data;

@Data
public class ReviewDto {

    private int id;
    private int rating;
    private String comment;
    private User user;
    private Product product;

}
