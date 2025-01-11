package com.example.E_commerce.service;

import com.example.E_commerce.dto.OrderDto;
import com.example.E_commerce.exception.IdNotFoundException;
import com.example.E_commerce.mapper.OrderMapper;
import com.example.E_commerce.model.*;
import com.example.E_commerce.repository.CartItemRepository;
import com.example.E_commerce.repository.CartRepository;
import com.example.E_commerce.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;


    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CartRepository cartRepository, CartService cartService, UserService userService, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
    }

    public List<OrderDto> getAllOrder() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    public OrderDto findById(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IdNotFoundException(orderId));
        return orderMapper.toDto(order);
    }

    public OrderDto createOrder(Order order) {
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    public OrderDto updateOrder(int orderId, Order order) {
        Order currentOrder = orderRepository.findById(orderId).orElseThrow(() -> new IdNotFoundException(orderId));

        currentOrder.setOrderDate(order.getOrderDate());
        currentOrder.setOrderItemsList(order.getOrderItemsList());
        currentOrder.setPaymentType(order.getPaymentType());
        currentOrder.setTotalAmount(order.getTotalAmount());

        orderRepository.save(currentOrder);
        return orderMapper.toDto(currentOrder);
    }

    public void deleteOrder(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IdNotFoundException(orderId));
        if (order != null) {
            orderRepository.deleteById(orderId);
            log.info("Successfully Deleted");
        } else {
            log.info("Id Not Found !!");
        }

    }


    public OrderDto placeOrder(int cartId, String paymentType) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IdNotFoundException(cartId));

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentType(paymentType);

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getQuantity(), cartItem.getProduct().getPrice(), cartItem.getProduct(), order))
                .collect(Collectors.toList());

        if (orderItems.isEmpty()) {
            throw new RuntimeException("No items in cart to place an order");
        }
        order.setOrderItemsList(orderItems);

        double totalAmount = cartService.calculateToPrice(cartId);
        userService.getRecentUserBalance(cart.getUser().getId());

        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        List<CartItem> cartItems = cart.getCartItems();
        for(CartItem cartItem : cartItems ){
            cartItemRepository.delete(cartItem);
        }
        cart.getCartItems().clear();
        cartRepository.save(cart);
        return orderMapper.toDto(order);
    }




}
