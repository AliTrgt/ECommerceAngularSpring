package com.example.E_commerce.repository;

import com.example.E_commerce.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {


    List<Review> findReviewByUserId(int userId);


}
