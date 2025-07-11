package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Visitor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class VisitorRepository {
    private final List<Visitor> visitors = new ArrayList<>();
    private long Id = 0;

    public void save(Visitor visitor) {
        if (visitor.getId() == null) {
            visitor.setId(++Id);
        }
        visitors.add(visitor);
    }

    public void remove(Visitor visitor) {
        visitors.remove(visitor);
    }

    public List<Visitor> findAll() {
        return Collections.unmodifiableList(visitors);
    }
}
