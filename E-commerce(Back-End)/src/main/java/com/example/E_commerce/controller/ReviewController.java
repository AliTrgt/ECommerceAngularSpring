package com.example.E_commerce.controller;


import com.example.E_commerce.dto.ReviewDto;
import com.example.E_commerce.model.Review;
import com.example.E_commerce.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReview() {
        List<ReviewDto> reviewList = reviewService.getAllReview();
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> findReviewById(@PathVariable int reviewId) {
        ReviewDto reviewDto = reviewService.findById(reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ReviewDto> createReview(@RequestBody Review review) {
        ReviewDto reviewDto = reviewService.createReview(review);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable int reviewId, @RequestBody Review review) {
        ReviewDto reviewDto = reviewService.updateReview(reviewId, review);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable(name = "reviewId") int reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("/findReview")
    public ResponseEntity<List<ReviewDto>> findReviewByUser(@RequestParam int userId){
        List<ReviewDto> reviewList = reviewService.findReviewByUser(userId);
        return new ResponseEntity<>(reviewList,HttpStatus.OK);
    }


}
