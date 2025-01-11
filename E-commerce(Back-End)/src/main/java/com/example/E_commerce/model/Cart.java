package com.example.E_commerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TBL_CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    @Column(nullable = true)
    private List<CartItem> cartItems;

    public Cart(User user, List<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.user = user;
    }

    public Cart() {

    }

    public int getId() {
        return id;
    }

    public void setId(int cartId) {
        this.id = cartId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
