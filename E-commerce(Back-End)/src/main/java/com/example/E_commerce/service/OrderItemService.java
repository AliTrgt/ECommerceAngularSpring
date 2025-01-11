package com.example.E_commerce.service;

import com.example.E_commerce.dto.OrderDto;
import com.example.E_commerce.dto.OrderItemDto;
import com.example.E_commerce.exception.IdNotFoundException;
import com.example.E_commerce.mapper.OrderItemMapper;
import com.example.E_commerce.model.*;
import com.example.E_commerce.repository.OrderItemRepository;
import com.example.E_commerce.repository.OrderRepository;
import com.example.E_commerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderItemDto> getAllOrderItem() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();

        return orderItemList
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderItemDto findById(int orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new IdNotFoundException(orderItemId));
        return orderItemMapper.toDto(orderItem);
    }

    public OrderItemDto createOrderItem(OrderItem orderItem) {
        OrderItem tempOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toDto(tempOrderItem);
    }

    public OrderItemDto updateOrderItem(int orderItemId, OrderItem orderItem) {
        OrderItem currentOrderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new IdNotFoundException(orderItemId));

        currentOrderItem.setOrder(orderItem.getOrder());
        currentOrderItem.setPrice(orderItem.getPrice());
        currentOrderItem.setProduct(orderItem.getProduct());
        currentOrderItem.setQuantity(orderItem.getQuantity());

        orderItemRepository.save(currentOrderItem);
        return orderItemMapper.toDto(currentOrderItem);
    }


    public void deleteOrderItem(int orderItemId) {
        OrderItem user = orderItemRepository.findById(orderItemId).orElseThrow(() -> new IdNotFoundException(orderItemId));
        if (user != null) {
            orderItemRepository.deleteById(orderItemId);
            log.info("Successfully Deleted");
        } else {
            log.info("Id Not Found !!");
        }
    }



}
