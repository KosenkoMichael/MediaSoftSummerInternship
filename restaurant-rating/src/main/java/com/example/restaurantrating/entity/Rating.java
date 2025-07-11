package com.example.restaurantrating.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Long visitorId;
    private Long restaurantId;
    private Integer score;
    private String review;
}
