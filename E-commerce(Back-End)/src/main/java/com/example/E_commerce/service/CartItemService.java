package com.example.E_commerce.service;

import com.example.E_commerce.dto.CartItemDto;
import com.example.E_commerce.dto.ProductDto;
import com.example.E_commerce.exception.IdNotFoundException;
import com.example.E_commerce.mapper.CartItemMapper;
import com.example.E_commerce.model.Cart;
import com.example.E_commerce.model.CartItem;
import com.example.E_commerce.model.Product;
import com.example.E_commerce.repository.CartItemRepository;
import com.example.E_commerce.repository.CartRepository;
import com.example.E_commerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItemService(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, CartRepository cartRepository, ProductRepository productRepository, ConversionService conversionService) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<CartItemDto> getAllCartItem() {
        List<CartItem> cartItemList = cartItemRepository.findAll();
        return cartItemList.stream().map(cartItemMapper::toDto).collect(Collectors.toList());
    }

    public CartItemDto findById(int cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IdNotFoundException(cartItemId));
        return cartItemMapper.toDto(cartItem);
    }

    public CartItemDto createCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    public CartItemDto updateCartItem(int cartItemId, CartItem updatedCartItem) {
        // CartItem'ı ID'ye göre bul
        CartItem currentCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IdNotFoundException(cartItemId));

        // Mevcut CartItem miktarını al
        int currentQuantity = currentCartItem.getQuantity();

        // CartItem miktarını yeni değer ile güncelle
        currentCartItem.setQuantity(updatedCartItem.getQuantity());

        // CartItem ile ilişkili ürünü al
        Product product = currentCartItem.getProduct();

        // Miktar farkını hesapla (yeni miktar - eski miktar)
        int quantityDifference = updatedCartItem.getQuantity() - currentQuantity;

        // Ürünün stok bilgisini miktar farkına göre güncelle
        product.setStock(product.getStock() - quantityDifference);

        // Ürünü ve CartItem'ı kaydet
        productRepository.save(product);
        cartItemRepository.save(currentCartItem);

        return cartItemMapper.toDto(currentCartItem);
    }




    @Transactional
    public void deleteCartItem(int cartItemId) {
        // Sepetteki CartItem'ı buluyoruz
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IdNotFoundException(cartItemId));

        // Silinen CartItem'dan ürünü alıyoruz
        Product product = cartItem.getProduct();

        // Ürünün stoğunu geri artırıyoruz
        product.setStock(product.getStock() + cartItem.getQuantity());
        productRepository.save(product);

        // CartItem'ı siliyoruz
        cartItemRepository.deleteById(cartItemId);
    }


}
