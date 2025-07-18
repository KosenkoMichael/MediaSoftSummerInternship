package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.request.RestaurantRequestDTO;
import com.example.restaurantrating.dto.response.RestaurantResponseDTO;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.exception.ResourceNotFoundException;
import com.example.restaurantrating.mapper.RestaurantMapper;
import com.example.restaurantrating.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public void save(RestaurantRequestDTO dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurantRepository.save(restaurant);
    }

    public void remove(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant with ID " + id + " not found"));
        restaurantRepository.delete(restaurant);
    }

    public List<RestaurantResponseDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<RestaurantResponseDTO> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable)
                .map(restaurantMapper::toResponseDTO);
    }

    public RestaurantResponseDTO findById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant with ID " + id + " not found"));
        return restaurantMapper.toResponseDTO(restaurant);
    }

    public Page<RestaurantResponseDTO> findByMinRating(BigDecimal minRating, Pageable pageable) {
        Page<Restaurant> page = restaurantRepository.findByAverageRatingGreaterThanEqual(minRating, pageable);
        return page.map(restaurantMapper::toResponseDTO);
    }
}
