package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.request.RestaurantRequestDTO;
import com.example.restaurantrating.dto.response.RestaurantResponseDTO;
import com.example.restaurantrating.mapper.RestaurantMapper;
import com.example.restaurantrating.repository.RestaurantRepository;
import com.example.restaurantrating.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        restaurantRepository.findAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .ifPresent(restaurantRepository::remove);
    }

    public List<RestaurantResponseDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
