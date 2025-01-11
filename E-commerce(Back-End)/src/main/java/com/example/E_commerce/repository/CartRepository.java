package com.example.E_commerce.repository;

import com.example.E_commerce.model.Cart;
import com.example.E_commerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findCartByCartItems(CartItem cartItem);
    Cart findCartByUserId(int userId);

}
