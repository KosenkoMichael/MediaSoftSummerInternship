package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Rating;
import com.example.restaurantrating.entity.RatingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {

}
