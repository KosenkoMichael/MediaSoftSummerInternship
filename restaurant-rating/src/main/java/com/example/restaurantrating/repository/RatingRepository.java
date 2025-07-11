package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Rating;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class RatingRepository {
    private final List<Rating> ratings = new ArrayList<>();

    public void save(Rating rating) {
        ratings.add(rating);
    }

    public void remove(Rating rating) {
        ratings.remove(rating);
    }

    public List<Rating> findAll() {
        return Collections.unmodifiableList(ratings);
    }

    public Optional<Rating> findById(Long visitorId, Long restaurantId) {
        return ratings.stream()
                .filter(r -> r.getVisitorId().equals(visitorId) && r.getRestaurantId().equals(restaurantId))
                .findFirst();
    }
}
