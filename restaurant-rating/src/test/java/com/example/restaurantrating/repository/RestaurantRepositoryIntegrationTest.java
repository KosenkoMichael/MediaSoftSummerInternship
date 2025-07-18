package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Rating;
import com.example.restaurantrating.entity.RatingId;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.entity.Visitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class RestaurantRepositoryIntegrationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testFindByAverageRatingGreaterThanEqual() {
        Restaurant r1 = new Restaurant();
        r1.setName("Italian Bistro");
        r1.setAverageRating(new BigDecimal("4.5"));
        restaurantRepository.save(r1);

        Restaurant r2 = new Restaurant();
        r2.setName("Fast Food Place");
        r2.setAverageRating(new BigDecimal("3.0"));
        restaurantRepository.save(r2);

        List<Restaurant> result = restaurantRepository.findByAverageRatingGreaterThanEqual(new BigDecimal("4.0"));

        assertEquals(1, result.size());
        assertEquals("Italian Bistro", result.get(0).getName());
    }

    @Test
    public void testFindByAverageRatingGreaterThanEqualWithPaging() {
        for (int i = 1; i <= 10; i++) {
            Restaurant r = new Restaurant();
            r.setName("Restaurant " + i);
            r.setAverageRating(new BigDecimal(i % 5 + 1)); // рейтинги от 1 до 5
            restaurantRepository.save(r);
        }

        Page<Restaurant> page = restaurantRepository.findByAverageRatingGreaterThanEqual(
                new BigDecimal("3.0"),
                PageRequest.of(0, 5)
        );

        assertTrue(page.getContent().stream()
                .allMatch(r -> r.getAverageRating().compareTo(new BigDecimal("3.0")) >= 0));

        assertFalse(page.isEmpty());
        assertTrue(page.getContent().size() <= 5);
    }
}
