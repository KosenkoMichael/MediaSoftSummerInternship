package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.request.RatingRequestDTO;
import com.example.restaurantrating.dto.response.RatingResponseDTO;
import com.example.restaurantrating.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRating(@Valid @RequestBody RatingRequestDTO dto) {
        ratingService.save(dto);
    }

    @GetMapping
    public List<RatingResponseDTO> getAllRatings() {
        return ratingService.findAll();
    }

    @DeleteMapping("/visitor/{visitorId}/restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable Long visitorId, @PathVariable Long restaurantId) {
        ratingService.remove(visitorId, restaurantId);
    }
}
