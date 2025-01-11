package com.example.E_commerce.controller;

import com.example.E_commerce.dto.ProductDto;
import com.example.E_commerce.model.Product;
import com.example.E_commerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProduct(){
            List<ProductDto> productDtoList = productService.getAllProduct();
            return new ResponseEntity<>(productDtoList,HttpStatus.OK);
    }



    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable int productId) {
        ProductDto product = productService.findById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody Product product) {
        ProductDto productDto = productService.createProduct(product);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int productId, @RequestBody Product product) {
        ProductDto productDto = productService.updateProduct(productId, product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId){
            productService.deleteProducts(productId);
            return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
    }


    @PostMapping("/addProductToCart")
    public void addProductToCart(@RequestParam int productId,@RequestParam int cartId,@RequestParam int quantity){
            productService.addProductToCart(productId,cartId,quantity);
    }


    @DeleteMapping("/deleteProductToCart/{cartItemId}")
    public void deleteProductToCart(@PathVariable int cartItemId){
            productService.deleteProductToCart(cartItemId);
    }

    @GetMapping("/find/{categoryName}")
    public ResponseEntity<List<ProductDto>> findProductsByCategoryName(@PathVariable String categoryName){
            List<ProductDto> productList = productService.findProductsByCategoryName(categoryName);
            return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @GetMapping("/filterByPrice")
    public ResponseEntity<List<ProductDto>> findProductByPriceRange(@RequestParam(required = false) int low,@RequestParam(required = false) int high){
            List<ProductDto> productList = productService.findProductByPriceRange(low,high);

            if(productList.isEmpty()){
                    return ResponseEntity.noContent().build();
            }

            return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @GetMapping("/filterByName")
    public ResponseEntity<List<ProductDto>> findProductsByNameContaining(@RequestParam(required = false) String productName){
            List<ProductDto> productsList = productService.findProductsByNameLike(productName);
            return new ResponseEntity<>(productsList,HttpStatus.OK);
    }


}
