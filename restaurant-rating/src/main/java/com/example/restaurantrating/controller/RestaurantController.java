package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.request.RestaurantRequestDTO;
import com.example.restaurantrating.dto.response.RestaurantResponseDTO;
import com.example.restaurantrating.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRestaurant(@Valid @RequestBody RestaurantRequestDTO dto) {
        restaurantService.save(dto);
    }

    @GetMapping
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.remove(id);
    }
}
