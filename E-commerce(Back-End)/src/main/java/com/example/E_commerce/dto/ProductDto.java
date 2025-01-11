package com.example.E_commerce.dto;


import com.example.E_commerce.model.Category;
import com.example.E_commerce.model.User;
import lombok.Data;

import java.util.List;


@Data
public class ProductDto {

    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String imageUrl;
    private Category category;
    private List<ReviewDto> reviewList;





}
