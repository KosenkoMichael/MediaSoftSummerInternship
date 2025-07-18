package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.request.RatingRequestDTO;
import com.example.restaurantrating.dto.response.RatingResponseDTO;
import com.example.restaurantrating.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public void save(@RequestBody @Valid RatingRequestDTO dto) {
        ratingService.save(dto);
    }

    @DeleteMapping("/{visitorId}/{restaurantId}")
    public void delete(@PathVariable Long visitorId, @PathVariable Long restaurantId) {
        ratingService.remove(visitorId, restaurantId);
    }

    @GetMapping
    public Page<RatingResponseDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "score") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return ratingService.findAll(pageRequest);
    }
}
