package com.example.restaurantrating.mapper;

import com.example.restaurantrating.dto.request.RatingRequestDTO;
import com.example.restaurantrating.dto.response.RatingResponseDTO;
import com.example.restaurantrating.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    @Mapping(target = "visitor", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Rating toEntity(RatingRequestDTO dto);

    @Mapping(source = "visitor.id", target = "visitorId")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    RatingResponseDTO toResponseDTO(Rating entity);
}
