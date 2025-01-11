package com.example.E_commerce.dto;


import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {

    private int id;
    private String name;
    private List<ProductDto> productList;

}
