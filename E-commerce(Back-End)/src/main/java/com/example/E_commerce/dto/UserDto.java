package com.example.E_commerce.dto;


import com.example.E_commerce.model.*;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private double balance;
    private CartDto cart;
    private List<Role> authorities;
    private List<Review> reviewList;
    private List<Order> orderList;

}

