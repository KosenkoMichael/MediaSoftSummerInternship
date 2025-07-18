package com.example.restaurantrating.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingId implements Serializable {
    private Long visitor;
    private Long restaurant;
}
