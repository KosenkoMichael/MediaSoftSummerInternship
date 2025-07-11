package com.example.restaurantrating.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restaurantrating.repository.VisitorRepository;

import lombok.RequiredArgsConstructor;
import com.example.restaurantrating.entity.Visitor;

@Service
@RequiredArgsConstructor
public class VisitorService {
    private final VisitorRepository visitorRepository;

    public void save(Visitor visitor) {
        visitorRepository.save(visitor);
    }

    public void remove(Visitor visitor) {
        visitorRepository.remove(visitor);
    }

    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }
}
