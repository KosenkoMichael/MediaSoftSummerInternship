package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.request.RatingRequestDTO;
import com.example.restaurantrating.dto.response.RatingResponseDTO;
import com.example.restaurantrating.entity.Rating;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.mapper.RatingMapper;
import com.example.restaurantrating.repository.RatingRepository;
import com.example.restaurantrating.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;
    private final RatingMapper ratingMapper;

    public void save(RatingRequestDTO dto) {
        Rating rating = ratingMapper.toEntity(dto);
        ratingRepository.save(rating);
        recalculateAverageRating(rating.getRestaurantId());
    }

    public void remove(Long visitorId, Long restaurantId) {
        findById(visitorId, restaurantId).ifPresent(rating -> {
            ratingRepository.remove(rating);
            recalculateAverageRating(restaurantId);
        });
    }

    public List<RatingResponseDTO> findAll() {
        return ratingRepository.findAll().stream()
                .map(ratingMapper::toResponseDTO)
                .collect(Collectors.toList());
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
