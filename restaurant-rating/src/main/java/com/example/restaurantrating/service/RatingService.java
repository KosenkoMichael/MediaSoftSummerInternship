package com.example.restaurantrating.service;

import com.example.restaurantrating.entity.Rating;
import com.example.restaurantrating.repository.RatingRepository;
import com.example.restaurantrating.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;

    public void save(Rating rating) {
        ratingRepository.save(rating);
        recalculateAverageRating(rating.getRestaurantId());
    }

    public void remove(Rating rating) {
        ratingRepository.remove(rating);
        recalculateAverageRating(rating.getRestaurantId());
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> findById(Long visitorId, Long restaurantId) {
        return ratingRepository.findById(visitorId, restaurantId);
    }

    private void recalculateAverageRating(Long restaurantId) {
        List<Rating> ratings = ratingRepository.findAll();
        double avg = ratings.stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);

        restaurantRepository.findAll().stream()
                .filter(r -> r.getId().equals(restaurantId))
                .findFirst()
                .ifPresent(restaurant -> restaurant.setAverageRating(BigDecimal.valueOf(avg)));
    }
}
