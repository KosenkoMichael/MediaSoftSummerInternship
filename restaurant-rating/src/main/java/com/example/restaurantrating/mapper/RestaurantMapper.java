package com.example.restaurantrating.mapper;

import com.example.restaurantrating.dto.request.RestaurantRequestDTO;
import com.example.restaurantrating.dto.response.RestaurantResponseDTO;
import com.example.restaurantrating.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    @Mapping(source = "kitchenType", target = "kitchentype")
    Restaurant toEntity(RestaurantRequestDTO dto);

    @Mapping(source = "kitchentype", target = "kitchenType")
    RestaurantResponseDTO toResponseDTO(Restaurant entity);
}
