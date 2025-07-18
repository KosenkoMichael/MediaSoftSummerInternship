package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Rating;
import com.example.restaurantrating.entity.RatingId;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.entity.Visitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class RatingRepositoryIntegrationTest {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testSaveAndFindRating() {
        Visitor visitor = new Visitor();
        visitor.setName("John Doe");
        visitor = visitorRepository.save(visitor);

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Fancy Restaurant");
        restaurant.setAverageRating(BigDecimal.valueOf(4.5));
        restaurant = restaurantRepository.save(restaurant);

        Rating rating = new Rating();
        rating.setVisitor(visitor);
        rating.setRestaurant(restaurant);
        rating.setScore(5);
        rating.setReview("Excellent food!");

        rating = ratingRepository.save(rating);

        RatingId ratingId = new RatingId(visitor.getId(), restaurant.getId());

        Optional<Rating> found = ratingRepository.findById(ratingId);
        assertTrue(found.isPresent());
        assertEquals(5, found.get().getScore());
        assertEquals("Excellent food!", found.get().getReview());
        assertEquals(visitor.getId(), found.get().getVisitor().getId());
        assertEquals(restaurant.getId(), found.get().getRestaurant().getId());
    }
}



