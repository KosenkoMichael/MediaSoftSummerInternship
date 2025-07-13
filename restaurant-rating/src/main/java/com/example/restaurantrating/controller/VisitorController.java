package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.request.VisitorRequestDTO;
import com.example.restaurantrating.dto.response.VisitorResponseDTO;
import com.example.restaurantrating.service.VisitorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVisitor(@Valid @RequestBody VisitorRequestDTO dto) {
        visitorService.save(dto);
    }

    @GetMapping
    public List<VisitorResponseDTO> getAllVisitors() {
        return visitorService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVisitor(@PathVariable Long id) {
        visitorService.remove(id);
    }
}
