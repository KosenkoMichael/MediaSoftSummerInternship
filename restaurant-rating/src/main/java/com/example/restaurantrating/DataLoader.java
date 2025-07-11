package com.example.restaurantrating;

import com.example.restaurantrating.entity.*;
import com.example.restaurantrating.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final RatingService ratingService;

    public DataLoader(VisitorService visitorService, RestaurantService restaurantService, RatingService ratingService) {
        this.visitorService = visitorService;
        this.restaurantService = restaurantService;
        this.ratingService = ratingService;
    }

    @Override
    public void run(String... args) {
        Visitor v1 = new Visitor(null, "Alex", 28, "male");
        Visitor v2 = new Visitor(null, null, 35, "female");
        visitorService.save(v1);
        visitorService.save(v2);

        Restaurant r1 = new Restaurant(null, "Pasta Presto", "Italian food", KitchenType.ITALIAN, new BigDecimal("1500"), BigDecimal.ZERO);
        Restaurant r2 = new Restaurant(null, "Dragon's tea", "", KitchenType.CHINESE, new BigDecimal("900"), BigDecimal.ZERO);
        restaurantService.save(r1);
        restaurantService.save(r2);

        ratingService.save(new Rating(v1.getId(), r1.getId(), 5, "Awesome!"));
        ratingService.save(new Rating(v2.getId(), r1.getId(), 4, null));
        ratingService.save(new Rating(v1.getId(), r2.getId(), 3, "Not bad, but expensive"));

        System.out.println("All visitors:");
        visitorService.findAll().forEach(System.out::println);

        System.out.println("\nAll restaurants:");
        restaurantService.findAll().forEach(System.out::println);

        System.out.println("\nAll ratings:");
        ratingService.findAll().forEach(System.out::println);
    }
}
