package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Rating;
import com.example.restaurantrating.entity.RatingId;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.entity.Visitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class VisitorRepositoryIntegrationTest {

    @Autowired
    private VisitorRepository visitorRepository;

    @Test
    public void testSaveAndFindVisitor() {
        Visitor visitor = new Visitor();
        visitor.setName("Test Visitor");
        visitor = visitorRepository.save(visitor);

        Optional<Visitor> found = visitorRepository.findById(visitor.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Visitor", found.get().getName());
    }
}
