package com.example.E_commerce.repository;

import com.example.E_commerce.model.OrderItem;
import com.example.E_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {


}
