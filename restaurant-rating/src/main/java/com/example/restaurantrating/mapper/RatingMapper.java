package com.example.restaurantrating.mapper;

import com.example.restaurantrating.dto.request.RatingRequestDTO;
import com.example.restaurantrating.dto.response.RatingResponseDTO;
import com.example.restaurantrating.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    Rating toEntity(RatingRequestDTO dto);

    RatingResponseDTO toResponseDTO(Rating entity);
}
