package com.example.E_commerce.mapper;

import com.example.E_commerce.dto.OrderDto;
import com.example.E_commerce.model.Order;
import com.example.E_commerce.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDto toDto(Order order){
        return modelMapper.map(order,OrderDto.class);
    }

    public Order toEntity(OrderDto orderDto){
            return modelMapper.map(orderDto,Order.class);
    }

}
