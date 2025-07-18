package com.example.restaurantrating.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@IdClass(RatingId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @ManyToOne
    @JoinColumn(name = "visitor_id", referencedColumnName = "id")
    private Visitor visitor;

    @Id
    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    private Integer score;

    private String review;
}
