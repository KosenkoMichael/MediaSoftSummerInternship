package com.example.restaurantrating.dto.response;

import java.math.BigDecimal;
import com.example.restaurantrating.entity.KitchenType;

public record RestaurantResponseDTO(
        Long id,
        String name,
        String description,
        KitchenType kitchenType,
        BigDecimal averageCheck,
        BigDecimal averageRating
) {}
