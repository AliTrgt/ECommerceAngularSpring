package com.example.E_commerce.repository;

import com.example.E_commerce.model.Cart;
import com.example.E_commerce.model.CartItem;
import com.example.E_commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

    Optional<CartItem> findCartItemByCartIdAndProductId(int cartId,int productId);

}
