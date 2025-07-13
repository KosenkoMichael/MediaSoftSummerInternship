package com.example.restaurantrating.dto.response;

import java.math.BigDecimal;

import com.example.restaurantrating.entity.KitchenType;
import lombok.Value;

@Value
public class RestaurantResponseDTO {
    Long id;
    String name;
    String description;
    KitchenType kitchenType;
    BigDecimal averageCheck;
    BigDecimal averageRating;
}