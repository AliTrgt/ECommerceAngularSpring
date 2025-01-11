package com.example.E_commerce.repository;

import com.example.E_commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {


        List<Product> findProductsByCategoryName(String categoryName);

        @Query(value = "Select p FROM Product p Where p.price between ?1 and ?2")
        List<Product> findProductByPriceRange(int low,int high);

        @Query("Select p from Product p Where p.name like  %:productName%")
        List<Product> findProductsByNameLike(String productName);



}
