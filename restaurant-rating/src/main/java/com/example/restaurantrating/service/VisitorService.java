package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.request.VisitorRequestDTO;
import com.example.restaurantrating.dto.response.VisitorResponseDTO;
import com.example.restaurantrating.mapper.VisitorMapper;
import com.example.restaurantrating.repository.VisitorRepository;
import com.example.restaurantrating.entity.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorMapper visitorMapper;

    public void save(VisitorRequestDTO dto) {
        Visitor visitor = visitorMapper.toEntity(dto);
        visitorRepository.save(visitor);
    }

    public void remove(Long id) {
        visitorRepository.findAll().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .ifPresent(visitorRepository::remove);
    }

    public List<VisitorResponseDTO> findAll() {
        return visitorRepository.findAll().stream()
                .map(visitorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
