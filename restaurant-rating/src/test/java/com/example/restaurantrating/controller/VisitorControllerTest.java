package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.request.VisitorRequestDTO;
import com.example.restaurantrating.dto.response.VisitorResponseDTO;
import com.example.restaurantrating.service.VisitorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitorController.class)
public class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorService visitorService;

    @Autowired
    private ObjectMapper objectMapper; // для сериализации/десериализации JSON

    @Test
    void save_shouldReturnOk() throws Exception {
        VisitorRequestDTO dto = VisitorRequestDTO.builder()
                .name("Alice")
                .age(25)
                .gender("Female")
                .build();

        doNothing().when(visitorService).save(any(VisitorRequestDTO.class));

        mockMvc.perform(post("/api/visitors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        Long id = 1L;

        doNothing().when(visitorService).remove(id);

        mockMvc.perform(delete("/api/visitors/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void findAll_shouldReturnPageOfVisitors() throws Exception {
        VisitorResponseDTO dto1 = new VisitorResponseDTO(1L, "John", 30, "Male");
        VisitorResponseDTO dto2 = new VisitorResponseDTO(2L, "Jane", 28, "Female");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Page<VisitorResponseDTO> page = new PageImpl<>(List.of(dto1, dto2), pageable, 2);

        when(visitorService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/visitors")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(dto1.id()))
                .andExpect(jsonPath("$.content[0].name").value(dto1.name()))
                .andExpect(jsonPath("$.content[0].age").value(dto1.age()))
                .andExpect(jsonPath("$.content[0].gender").value(dto1.gender()))
                .andExpect(jsonPath("$.content[1].id").value(dto2.id()))
                .andExpect(jsonPath("$.content[1].name").value(dto2.name()));
    }
}
