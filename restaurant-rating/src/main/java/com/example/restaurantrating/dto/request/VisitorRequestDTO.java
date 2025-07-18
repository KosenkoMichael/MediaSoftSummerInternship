package com.example.restaurantrating.dto.request;

import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VisitorRequestDTO {

    @NotBlank(message = "Name required")
    @Size(max = 100, message = "The name must be no longer than 100 characters")
    String name;

    @NotNull(message = "Age required")
    @Min(value = 0, message = "Age must be non negative")
    Integer age;

    @NotBlank(message = "Gender required")
    @Pattern(regexp = "^(Male|Female)$", message = "gender must be Male or Female")
    String gender;
}
