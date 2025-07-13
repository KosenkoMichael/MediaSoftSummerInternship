package com.example.restaurantrating.dto.request;

import java.math.BigDecimal;

import com.example.restaurantrating.entity.KitchenType;
import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RestaurantRequestDTO {

    @NotBlank(message = "Restaurant name required")
    @Size(max = 100, message = "The name must be no longer than 100 characters")
    String name;

    @NotBlank(message = "Description required")
    @Size(max = 1000, message = "The description must be no longer than 1000 characters.")
    String description;

    @NotNull(message = "Kitchen type required")
    KitchenType kitchenType;

    @NotNull(message = "Average bill required")
    @DecimalMin(value = "0.0", inclusive = false, message = "The average bill must be non negative")
    BigDecimal averageCheck;
}
