package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.request.RatingRequestDTO;
import com.example.restaurantrating.dto.response.RatingResponseDTO;
import com.example.restaurantrating.entity.*;
import com.example.restaurantrating.mapper.RatingMapper;
import com.example.restaurantrating.repository.RatingRepository;
import com.example.restaurantrating.repository.RestaurantRepository;
import com.example.restaurantrating.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private RatingMapper ratingMapper;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void save_shouldSaveRatingAndRecalculateAverage() {
        RatingRequestDTO dto = RatingRequestDTO.builder()
                .visitorId(1L)
                .restaurantId(2L)
                .score(4)
                .review("Great!")
                .build();

        Visitor visitor = new Visitor(1L, "John", 30, "Male");
        Restaurant restaurant = new Restaurant(2L, "Test Restaurant", "Desc", KitchenType.ITALIAN, BigDecimal.valueOf(20), BigDecimal.ZERO);
        Rating ratingEntity = new Rating();
        ratingEntity.setScore(dto.getScore());

        when(visitorRepository.findById(dto.getVisitorId())).thenReturn(Optional.of(visitor));
        when(restaurantRepository.findById(dto.getRestaurantId())).thenReturn(Optional.of(restaurant));
        when(ratingMapper.toEntity(dto)).thenReturn(ratingEntity);
        when(ratingRepository.findAll()).thenReturn(Collections.singletonList(ratingEntity));
        when(restaurantRepository.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ratingService.save(dto);

        verify(ratingRepository).save(ratingEntity);
        verify(restaurantRepository).save(argThat(r -> r.getAverageRating().doubleValue() == 4.0));
    }

    @Test
    void remove_shouldDeleteRatingAndRecalculateAverage() {
        Long visitorId = 1L;
        Long restaurantId = 2L;

        RatingId ratingId = new RatingId(visitorId, restaurantId);

        Visitor visitor = new Visitor(visitorId, "VisitorName", 30, "Male");
        Restaurant restaurant = new Restaurant(restaurantId, "Test Restaurant", "Desc", KitchenType.ITALIAN, BigDecimal.TEN, BigDecimal.ZERO);

        Rating rating = new Rating();
        rating.setVisitor(visitor);
        rating.setRestaurant(restaurant);
        rating.setScore(5);
        rating.setReview("Great!");

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        when(ratingRepository.findAll()).thenReturn(Collections.emptyList());
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        ratingService.remove(visitorId, restaurantId);

        verify(ratingRepository).delete(rating);
        verify(restaurantRepository).save(argThat(r -> r.getAverageRating().doubleValue() == 0.0));
    }

    @Test
    void findAll_shouldReturnListOfRatingResponseDTOs() {
        Visitor visitor = new Visitor(1L, "Alice", 30, "Female");
        Restaurant restaurant = new Restaurant(2L, "Test Restaurant", "Desc", null, null, null);
        Rating rating = new Rating(visitor, restaurant, 4, "Good");

        RatingResponseDTO dto = new RatingResponseDTO(
                visitor.getId(),
                restaurant.getId(),
                rating.getScore(),
                rating.getReview()
        );

        when(ratingRepository.findAll()).thenReturn(Collections.singletonList(rating));
        when(ratingMapper.toResponseDTO(rating)).thenReturn(dto);

        List<RatingResponseDTO> result = ratingService.findAll();

        assertThat(result).containsExactly(dto);
    }


    @Test
    void findAll_withPageable_shouldReturnPageOfRatingResponseDTOs() {
        Visitor visitor = new Visitor(1L, "Alice", 30, "Female");
        Restaurant restaurant = new Restaurant(2L, "Test Restaurant", "Description", null, null, null);
        Rating rating = new Rating(visitor, restaurant, 5, "Excellent!");

        RatingResponseDTO dto = new RatingResponseDTO(
                visitor.getId(),
                restaurant.getId(),
                rating.getScore(),
                rating.getReview()
        );

        Page<Rating> ratingPage = new PageImpl<>(List.of(rating));
        Pageable pageable = PageRequest.of(0, 10);

        when(ratingRepository.findAll(pageable)).thenReturn(ratingPage);
        when(ratingMapper.toResponseDTO(rating)).thenReturn(dto);

        Page<RatingResponseDTO> result = ratingService.findAll(pageable);

        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void findById_shouldReturnOptionalRating() {
        Long visitorId = 1L;
        Long restaurantId = 2L;
        RatingId ratingId = new RatingId(visitorId, restaurantId);
        Rating rating = new Rating();

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));

        Optional<Rating> result = ratingService.findById(visitorId, restaurantId);

        assertThat(result).contains(rating);
    }
}
