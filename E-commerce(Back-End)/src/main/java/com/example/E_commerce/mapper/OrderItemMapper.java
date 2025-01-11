package com.example.E_commerce.mapper;

import com.example.E_commerce.dto.OrderItemDto;
import com.example.E_commerce.model.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    private final ModelMapper modelMapper;

    public OrderItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderItemDto toDto(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemDto.class);
    }

    public OrderItem toEntity(OrderItemDto orderItemDto) {
        return modelMapper.map(orderItemDto, OrderItem.class);
    }

}
