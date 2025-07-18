package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}
