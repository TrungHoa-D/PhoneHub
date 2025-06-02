package com.example.projectbase.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewResponseDto {
    private String id;

    private String userId;

    private String phoneId;

    private int rating;

    private String comment;

    private LocalDateTime createdAt;
}
