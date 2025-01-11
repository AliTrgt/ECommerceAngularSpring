package com.example.E_commerce.mapper;

import com.example.E_commerce.dto.ReviewDto;
import com.example.E_commerce.model.Review;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class ReviewMapper {

    private final ModelMapper modelMapper;

    public ReviewMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReviewDto toDto(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    public Review toEntity(ReviewDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }

}
