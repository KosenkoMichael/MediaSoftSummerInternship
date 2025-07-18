package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByAverageRatingGreaterThanEqual(BigDecimal minRating);

    @Query("SELECT r FROM Restaurant r WHERE r.averageRating >= :minRating")
    Page<Restaurant> findByAverageRatingGreaterThanEqual(BigDecimal minRating, Pageable pageable);
}
