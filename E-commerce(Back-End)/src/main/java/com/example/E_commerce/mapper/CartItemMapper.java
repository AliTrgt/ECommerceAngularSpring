package com.example.E_commerce.mapper;


import com.example.E_commerce.dto.CartItemDto;
import com.example.E_commerce.model.CartItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    private final ModelMapper modelMapper;

    public CartItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartItemDto toDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDto.class);
    }

    public CartItem toEntity(CartItemDto cartItemDto) {
        return modelMapper.map(cartItemDto, CartItem.class);
    }

}
