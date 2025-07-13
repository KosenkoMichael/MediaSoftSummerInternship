package com.example.restaurantrating.dto.response;

import lombok.Value;

@Value
public class VisitorResponseDTO {
    Long id;
    String name;
    Integer age;
    String gender;
}