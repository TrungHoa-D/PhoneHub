package com.example.projectbase.service.impl;

import com.example.projectbase.constant.CommonConstant;
import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.domain.dto.request.ReviewCreateDto;
import com.example.projectbase.domain.dto.request.ReviewUpdateDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.ReviewResponseDto;
import com.example.projectbase.domain.entity.Phone;
import com.example.projectbase.domain.entity.Review;
import com.example.projectbase.domain.entity.User;
import com.example.projectbase.domain.mapper.ReviewMapper;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.PhoneRepository;
import com.example.projectbase.repository.ReviewRepository;
import com.example.projectbase.repository.UserRepository;
import com.example.projectbase.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDto getReview(String id) {
        return reviewMapper.toReviewResponseDto(reviewRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorMessage.Review.ERR_NOT_FOUND_ID, new String[]{id}
        )));
    }

    @Override
    public List<ReviewResponseDto> getAllReviews() {
        return reviewMapper.toReviewResponseDto(reviewRepository.findAll());
    }

    @Override
    public List<ReviewResponseDto> getReviewsByPhoneId(String phoneId) {
        return reviewMapper.toReviewResponseDto(reviewRepository.findAllByPhone_Id(phoneId));
    }

    @Override
    public List<ReviewResponseDto> getReviewsByUserId(String userId) {
        return reviewMapper.toReviewResponseDto(reviewRepository.findAllByUser_Id(userId));
    }

    @Override
    public ReviewResponseDto createReview(ReviewCreateDto reviewCreateDto) {
        Review review = reviewMapper.toReview(reviewCreateDto);
        if(reviewRepository.findByPhone_IdAndUser_Id(reviewCreateDto.getPhoneId(), reviewCreateDto.getUserId()) == null) {
            User user = userRepository.findById(reviewCreateDto.getUserId()).orElseThrow(
                    () -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{reviewCreateDto.getUserId()})
            );
            Phone phone = phoneRepository.findById(reviewCreateDto.getPhoneId()).orElseThrow(
                    () -> new NotFoundException(ErrorMessage.Phone.ERR_NOT_FOUND_ID, new String[]{reviewCreateDto.getPhoneId()})
            );
            review.setPhone(phone);
            review.setUser(user);
            review.setCreatedAt(LocalDateTime.now());
            return reviewMapper.toReviewResponseDto(reviewRepository.save(review));
        }
        else throw new RuntimeException(ErrorMessage.Review.ERR_REVIEW_EXISTED);
    }

    @Override
    public ReviewResponseDto updateReview(ReviewUpdateDto reviewUpdateDto) {
        Review review = reviewRepository.findById(reviewUpdateDto.getId()).orElseThrow(() -> new NotFoundException(
                ErrorMessage.Review.ERR_NOT_FOUND_ID, new String[]{reviewUpdateDto.getId()}));
        review.setComment(reviewUpdateDto.getComment());
        review.setRating(reviewUpdateDto.getRating());
        return reviewMapper.toReviewResponseDto(reviewRepository.save(review));
    }

    @Override
    public CommonResponseDto deleteReview(String id) {
        reviewRepository.deleteById(id);
        return new CommonResponseDto(true, CommonConstant.SERVICE_SUCCESS);
    }
}
