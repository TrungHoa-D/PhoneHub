package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.request.ReviewCreateDto;
import com.example.projectbase.domain.dto.request.ReviewUpdateDto;
import com.example.projectbase.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
@Valid
public class ReviewController {
    private final ReviewService reviewService;

    @Tag(name = "review-controller")
    @Operation(summary = "API get all reviews")
    @GetMapping(UrlConstant.Review.GET_ALL_REVIEW)
    public ResponseEntity<?> getAllReviews() {
        return VsResponseUtil.success(reviewService.getAllReviews());
    }

    @Tag(name = "review-controller")
    @Operation(summary = "API get review by id")
    @GetMapping(UrlConstant.Review.GET_REVIEW_BY_ID)
    public ResponseEntity<?> getReviewById(@RequestParam String id) {
        return VsResponseUtil.success(reviewService.getReview(id));
    }

    @Tag(name = "review-controller")
    @Operation(summary = "API get review by user id")
    @GetMapping(UrlConstant.Review.GET_REVIEW_BY_USER_ID)
    public ResponseEntity<?> getReviewByUserId(@RequestParam String id) {
        return VsResponseUtil.success(reviewService.getReviewsByUserId(id));
    }

    @Tag(name = "review-controller")
    @Operation(summary = "API get review by phone id")
    @GetMapping(UrlConstant.Review.GET_REVIEW_BY_PHONE_ID)
    public ResponseEntity<?> getReviewByPhoneId(@RequestParam String id) {
        return VsResponseUtil.success(reviewService.getReviewsByPhoneId(id));
    }

    @Tag(name = "review-controller")
    @Operation(summary = "API create new review")
    @PostMapping(UrlConstant.Review.CREATE_REVIEW)
    public ResponseEntity<?> createReview(@RequestBody ReviewCreateDto reviewCreateDto) {
        return VsResponseUtil.success(reviewService.createReview(reviewCreateDto));
    }

    @Tag(name = "review-controller")
    @Operation(summary = "API update review")
    @PutMapping(UrlConstant.Review.UPDATE_REVIEW)
    public ResponseEntity<?> updateReview(@RequestBody ReviewUpdateDto reviewUpdateDto) {
        return VsResponseUtil.success(reviewService.updateReview(reviewUpdateDto));
    }

    @Tag(name = "review-controller")
    @Operation(summary = "API delete review")
    @DeleteMapping(UrlConstant.Review.DELETE_REVIEW)
    public ResponseEntity<?> deleteReview(@RequestParam String id) {
        return VsResponseUtil.success(reviewService.deleteReview(id));
    }
}
