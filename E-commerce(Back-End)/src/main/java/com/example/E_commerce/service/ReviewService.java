package com.example.E_commerce.service;


import com.example.E_commerce.mapper.ReviewMapper;
import com.example.E_commerce.dto.ReviewDto;
import com.example.E_commerce.exception.IdNotFoundException;
import com.example.E_commerce.model.Product;
import com.example.E_commerce.model.Review;
import com.example.E_commerce.model.User;
import com.example.E_commerce.repository.ProductRepository;
import com.example.E_commerce.repository.ReviewRepository;
import com.example.E_commerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;


    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    public List<ReviewDto> getAllReview() {
        List<Review> reviewList = reviewRepository.findAll();
        return reviewList.stream().map(reviewMapper::toDto).collect(Collectors.toList());
    }

    public ReviewDto findById(int reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IdNotFoundException(reviewId));
        return reviewMapper.toDto(review);
    }


    public ReviewDto createReview(Review review) {
        reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }


    public ReviewDto updateReview(int reviewId, Review review) {
        Review currentReview = reviewRepository.findById(reviewId).orElseThrow(() -> new IdNotFoundException(reviewId));

        currentReview.setComment(review.getComment());
        currentReview.setRating(review.getRating());

        reviewRepository.save(currentReview);
        return reviewMapper.toDto(currentReview);
    }

    public void deleteReview(int reviewId) {
            reviewRepository.deleteById(reviewId);
    }
    public List<ReviewDto> findReviewByUser(int userId){
        List<Review> reviewList = reviewRepository.findReviewByUserId(userId);
        return reviewList.stream().map(reviewMapper::toDto).collect(Collectors.toList());
    }


}
