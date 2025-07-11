package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class RestaurantRepository {
    private final List<Restaurant> restaurants = new ArrayList<>();
    private long Id = 0;

    public void save(Restaurant restaurant) {
        if (restaurant.getId() == null) {
            restaurant.setId(++Id);
        }
        restaurants.add(restaurant);
    }

    public void remove(Restaurant restaurant) {
        restaurants.remove(restaurant);
    }

    public List<Restaurant> findAll() {
        return Collections.unmodifiableList(restaurants);
    }
}
