package com.example.restaurantrating.dto.response;

public record RatingResponseDTO(
        Long visitorId,
        Long restaurantId,
        Integer score,
        String review
) {}