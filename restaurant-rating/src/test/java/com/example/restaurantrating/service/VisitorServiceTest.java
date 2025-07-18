package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.request.VisitorRequestDTO;
import com.example.restaurantrating.dto.response.VisitorResponseDTO;
import com.example.restaurantrating.entity.Visitor;
import com.example.restaurantrating.mapper.VisitorMapper;
import com.example.restaurantrating.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private VisitorMapper visitorMapper;

    @InjectMocks
    private VisitorService visitorService;

    @Test
    void save_shouldCallRepositoryWithMappedEntity() {
        VisitorRequestDTO dto = VisitorRequestDTO.builder()
                .name("Alice")
                .age(25)
                .gender("Female")
                .build();
        Visitor visitor = new Visitor(null, "Alice", 25, "Female");

        when(visitorMapper.toEntity(dto)).thenReturn(visitor);

        visitorService.save(dto);

        verify(visitorRepository).save(visitor);
    }

    @Test
    void remove_shouldCallDelete() {
        Long id = 1L;
        Visitor visitor = new Visitor(id, "John", 30, "Male");

        when(visitorRepository.findById(id)).thenReturn(Optional.of(visitor));

        visitorService.remove(id);

        verify(visitorRepository).delete(visitor);
    }

    @Test
    void findAll_shouldReturnListOfVisitorResponseDTOs() {
        Visitor v1 = new Visitor(1L, "John", 30, "Male");
        Visitor v2 = new Visitor(2L, "Jane", 28, "Female");

        VisitorResponseDTO dto1 = new VisitorResponseDTO(1L, "John", 30, "Male");
        VisitorResponseDTO dto2 = new VisitorResponseDTO(2L, "Jane", 28, "Female");

        when(visitorRepository.findAll()).thenReturn(Arrays.asList(v1, v2));
        when(visitorMapper.toResponseDTO(v1)).thenReturn(dto1);
        when(visitorMapper.toResponseDTO(v2)).thenReturn(dto2);

        List<VisitorResponseDTO> result = visitorService.findAll();

        assertThat(result).containsExactly(dto1, dto2);
    }

    @Test
    void findAll_withPageable_shouldReturnPageOfVisitorResponseDTOs() {
        Visitor v = new Visitor(1L, "John", 30, "Male");
        VisitorResponseDTO dto = new VisitorResponseDTO(1L, "John", 30, "Male");

        Page<Visitor> visitorPage = new PageImpl<>(List.of(v));
        Pageable pageable = PageRequest.of(0, 10);

        when(visitorRepository.findAll(pageable)).thenReturn(visitorPage);
        when(visitorMapper.toResponseDTO(v)).thenReturn(dto);

        Page<VisitorResponseDTO> result = visitorService.findAll(pageable);

        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void findById_shouldReturnVisitorResponseDTO() {
        Visitor visitor = new Visitor(1L, "John", 30, "Male");
        VisitorResponseDTO dto = new VisitorResponseDTO(1L, "John", 30, "Male");

        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
        when(visitorMapper.toResponseDTO(visitor)).thenReturn(dto);

        VisitorResponseDTO result = visitorService.findById(1L);

        assertThat(result.id()).isEqualTo(visitor.getId());
        assertThat(result.name()).isEqualTo(visitor.getName());
        assertThat(result.age()).isEqualTo(visitor.getAge());
        assertThat(result.gender()).isEqualTo(visitor.getGender());
    }
}

