package com.example.restaurantrating.dto.response;

import lombok.Value;

@Value
public class RatingResponseDTO {
    Long visitorId;
    Long restaurantId;
    Integer score;
    String review;
}