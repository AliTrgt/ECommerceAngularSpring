package com.example.E_commerce.mapper;

import com.example.E_commerce.dto.CartDto;
import com.example.E_commerce.model.Cart;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    private final ModelMapper modelMapper;

    public CartMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartDto toDto(Cart cart){
        return modelMapper.map(cart, CartDto.class);
    }

    public Cart toEntity(CartDto cartDto){
        return modelMapper.map(cartDto, Cart.class);
    }

}
