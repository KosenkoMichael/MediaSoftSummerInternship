package com.example.restaurantrating.mapper;

import com.example.restaurantrating.dto.request.VisitorRequestDTO;
import com.example.restaurantrating.dto.response.VisitorResponseDTO;
import com.example.restaurantrating.entity.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VisitorMapper {
    VisitorMapper INSTANCE = Mappers.getMapper(VisitorMapper.class);

    Visitor toEntity(VisitorRequestDTO dto);

    VisitorResponseDTO toResponseDTO(Visitor entity);
}