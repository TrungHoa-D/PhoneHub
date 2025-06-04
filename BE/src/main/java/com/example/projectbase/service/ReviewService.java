package com.example.projectbase.service;

import com.example.projectbase.domain.dto.request.ReviewCreateDto;
import com.example.projectbase.domain.dto.request.ReviewUpdateDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.ReviewResponseDto;

import java.util.List;

public interface ReviewService {
    ReviewResponseDto getReview(String id);
    List<ReviewResponseDto> getAllReviews();
    List<ReviewResponseDto> getReviewsByPhoneId(String phoneId);
    List<ReviewResponseDto> getReviewsByUserId(String userId);
    ReviewResponseDto createReview(ReviewCreateDto reviewCreateDto);
    ReviewResponseDto updateReview(ReviewUpdateDto reviewUpdateDto);
    CommonResponseDto deleteReview(String id);
}
