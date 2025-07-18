package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.request.VisitorRequestDTO;
import com.example.restaurantrating.dto.response.VisitorResponseDTO;
import com.example.restaurantrating.entity.Visitor;
import com.example.restaurantrating.exception.ResourceNotFoundException;
import com.example.restaurantrating.mapper.VisitorMapper;
import com.example.restaurantrating.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor with ID " + id + " not found"));
        visitorRepository.delete(visitor);
    }

    public List<VisitorResponseDTO> findAll() {
        return visitorRepository.findAll().stream()
                .map(visitorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<VisitorResponseDTO> findAll(Pageable pageable) {
        return visitorRepository.findAll(pageable)
                .map(visitorMapper::toResponseDTO);
    }

    public VisitorResponseDTO findById(Long id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor with ID " + id + " not found"));
        return visitorMapper.toResponseDTO(visitor);
    }
}
