package com.example.E_commerce.service;

import com.example.E_commerce.controller.FileStorageController;
import com.example.E_commerce.dto.ProductDto;
import com.example.E_commerce.exception.IdNotFoundException;
import com.example.E_commerce.mapper.ProductMapper;
import com.example.E_commerce.model.Cart;
import com.example.E_commerce.model.CartItem;
import com.example.E_commerce.model.Product;
import com.example.E_commerce.repository.CartItemRepository;
import com.example.E_commerce.repository.CartRepository;
import com.example.E_commerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    public List<ProductDto> getAllProduct(){
            List<Product> products =  productRepository.findAll();
            return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    public ProductDto findById(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IdNotFoundException(productId));
        return productMapper.toDto(product);
    }

    public ProductDto createProduct(Product product) {
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public ProductDto updateProduct(int productId, Product product) {
        Product currentProduct = productRepository.findById(productId).orElseThrow(() -> new IdNotFoundException(productId));

        currentProduct.setCategory(product.getCategory());
        currentProduct.setDescription(product.getDescription());
        currentProduct.setName(product.getName());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setReviewList(product.getReviewList());
        currentProduct.setStock(product.getStock());

        productRepository.save(currentProduct);
        return productMapper.toDto(currentProduct);
    }

    public void deleteProducts(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IdNotFoundException(productId));
        if (product != null) {
            productRepository.deleteById(productId);
            log.info("Successfully Deleted");
        } else {
            log.info("Id Not Found !!");
        }
    }


    public void addProductToCart(int productId,int cartId,int quantity){
            Product product = productRepository.findById(productId).orElseThrow(() -> new IdNotFoundException(productId));
            Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IdNotFoundException(cartId));

            int stock = product.getStock();
            int lastStock = stock - quantity;
            product.setStock(lastStock);

            Optional<CartItem> cartItem = cartItemRepository.findCartItemByCartIdAndProductId(cartId,productId);

            if(cartItem.isPresent()){
                    CartItem lastCartItem = cartItem.get();
                    lastCartItem.setQuantity(lastCartItem.getQuantity() + quantity);
                    cartItemRepository.save(lastCartItem);
            }
            else {
                CartItem tempCartItem = new CartItem(cart,product,quantity);
                cartItemRepository.save(tempCartItem);
            }
    }

    public void deleteProductToCart(int cartId){
        CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(() -> new IdNotFoundException(cartId));
        Product product = cartItem.getProduct();
        int productStock = product.getStock();
        int cartItemStock = cartItem.getQuantity();
        int lastStock = productStock + cartItemStock;
        product.setStock(lastStock);
        cartItemRepository.deleteById(cartId);
    }


    public List<ProductDto> findProductsByCategoryName(String categoryName){
            List<Product> products = productRepository.findProductsByCategoryName(categoryName);
            return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    public List<ProductDto> findProductByPriceRange(int low,int high){
        List<Product> productList = productRepository.findProductByPriceRange(low,high);
            return productList.
                    stream().
                    map(productMapper::toDto)
                    .collect(Collectors.toList());
    }

    public List<ProductDto> findProductsByNameLike(String productName){
            List<Product> products = productRepository.findProductsByNameLike(productName);
            return products
                    .stream()
                    .map(productMapper::toDto)
                    .collect(Collectors.toList());
    }





}
