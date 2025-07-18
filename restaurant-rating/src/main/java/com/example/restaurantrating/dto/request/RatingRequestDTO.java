package com.example.restaurantrating.dto.request;

import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RatingRequestDTO {

    @NotNull(message = "visitorId must not be null")
    Long visitorId;

    @NotNull(message = "restaurantId must not be null")
    Long restaurantId;

    @NotNull(message = "Score required")
    @Min(value = 0, message = "Min score is 1")
    @Max(value = 5, message = "Max score is 5")
    Integer score;

    @Size(max = 1000, message = "The review must not exceed 1000 characters")
    String review;
}
