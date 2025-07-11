package com.example.restaurantrating.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    private Long id;
    private String name;
    private String description;
    private KitchenType kitchentype;
    private BigDecimal averageCheck;
    private BigDecimal averageRating = BigDecimal.ZERO;
}
