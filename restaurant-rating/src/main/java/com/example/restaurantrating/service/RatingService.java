package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.request.RatingRequestDTO;
import com.example.restaurantrating.dto.response.RatingResponseDTO;
import com.example.restaurantrating.entity.Rating;
import com.example.restaurantrating.entity.RatingId;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.entity.Visitor;
import com.example.restaurantrating.mapper.RatingMapper;
import com.example.restaurantrating.repository.RatingRepository;
import com.example.restaurantrating.repository.RestaurantRepository;
import com.example.restaurantrating.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final VisitorRepository visitorRepository;
    private final RatingMapper ratingMapper;

    public void save(RatingRequestDTO dto) {
        Visitor visitor = visitorRepository.findById(dto.getVisitorId())
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Rating rating = ratingMapper.toEntity(dto);
        rating.setVisitor(visitor);
        rating.setRestaurant(restaurant);

        ratingRepository.save(rating);
        recalculateAverageRating(restaurant.getId());
    }

    public void remove(Long visitorId, Long restaurantId) {
        RatingId ratingId = new RatingId(visitorId, restaurantId);
        ratingRepository.findById(ratingId).ifPresent(rating -> {
            ratingRepository.delete(rating);
            recalculateAverageRating(restaurantId);
        });
    }

    public List<RatingResponseDTO> findAll() {
        return ratingRepository.findAll().stream()
                .map(ratingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<RatingResponseDTO> findAll(Pageable pageable) {
        return ratingRepository.findAll(pageable)
                .map(ratingMapper::toResponseDTO);
    }

    public Optional<Rating> findById(Long visitorId, Long restaurantId) {
        return ratingRepository.findById(new RatingId(visitorId, restaurantId));
    }

    private void recalculateAverageRating(Long restaurantId) {
        List<Rating> ratings = ratingRepository.findAll();

        double avg = ratings.stream()
                .filter(r -> r.getRestaurant().getId().equals(restaurantId))
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);

        restaurantRepository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setAverageRating(BigDecimal.valueOf(avg));
            restaurantRepository.save(restaurant);
        });
    }
}
