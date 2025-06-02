package com.example.projectbase.domain.mapper;

import com.example.projectbase.domain.dto.request.ReviewCreateDto;
import com.example.projectbase.domain.dto.request.ReviewUpdateDto;
import com.example.projectbase.domain.dto.response.ReviewResponseDto;
import com.example.projectbase.domain.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper (componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewCreateDto reviewCreateDto);

    Review toReview(ReviewUpdateDto reviewUpdateDto);

    @Mappings({
            @Mapping(target = "userId", source = "review.user.id"),
            @Mapping(target = "phoneId", source = "review.phone.id")
    })
    ReviewResponseDto toReviewResponseDto(Review review);

    List<ReviewResponseDto> toReviewResponseDto(List<Review> review);
}
