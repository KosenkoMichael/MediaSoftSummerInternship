package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.request.RestaurantRequestDTO;
import com.example.restaurantrating.dto.response.RestaurantResponseDTO;
import com.example.restaurantrating.entity.KitchenType;
import com.example.restaurantrating.exception.ResourceNotFoundException;
import com.example.restaurantrating.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void save_shouldReturnOk() throws Exception {
        RestaurantRequestDTO dto = RestaurantRequestDTO.builder()
                .name("Test Restaurant")
                .description("Test description")
                .kitchenType(KitchenType.ITALIAN)
                .averageCheck(BigDecimal.valueOf(25.5))
                .build();

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        Mockito.verify(restaurantService).save(any(RestaurantRequestDTO.class));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/restaurants/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(restaurantService).remove(id);
    }

    @Test
    void findAll_shouldReturnPagedResponse() throws Exception {
        RestaurantResponseDTO dto = new RestaurantResponseDTO(
                1L,
                "Test Restaurant",
                "Description",
                KitchenType.ITALIAN,
                BigDecimal.valueOf(20),
                BigDecimal.valueOf(4.5)
        );

        Page<RestaurantResponseDTO> page = new PageImpl<>(List.of(dto));

        Mockito.when(restaurantService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/restaurants")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(dto.id()))
                .andExpect(jsonPath("$.content[0].name").value(dto.name()))
                .andExpect(jsonPath("$.content[0].kitchenType").value(dto.kitchenType().toString()));

        Mockito.verify(restaurantService).findAll(any(Pageable.class));
    }

    @Test
    void findByMinRating_shouldReturnPagedResponse() throws Exception {
        BigDecimal minRating = BigDecimal.valueOf(3.5);

        RestaurantResponseDTO dto = new RestaurantResponseDTO(
                2L,
                "Rating Test Restaurant",
                "Description",
                KitchenType.MEXICAN,
                BigDecimal.valueOf(30),
                BigDecimal.valueOf(4.0)
        );

        Page<RestaurantResponseDTO> page = new PageImpl<>(List.of(dto));

        Mockito.when(restaurantService.findByMinRating(eq(minRating), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/restaurants/searchByRating")
                        .param("minRating", minRating.toString())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(dto.id()))
                .andExpect(jsonPath("$.content[0].averageRating").value(dto.averageRating().doubleValue()));

        Mockito.verify(restaurantService).findByMinRating(eq(minRating), any(Pageable.class));
    }

    @Test
    void save_shouldReturnBadRequest_whenInvalidData() throws Exception {
        RestaurantRequestDTO invalidDto = RestaurantRequestDTO.builder()
                .name("")
                .description("Desc")
                .kitchenType(null)
                .averageCheck(BigDecimal.valueOf(-10))
                .build();

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_shouldReturnNotFound_whenRestaurantDoesNotExist() throws Exception {
        Long nonExistentId = 999L;

        Mockito.doThrow(new ResourceNotFoundException("Restaurant not found"))
                .when(restaurantService).remove(nonExistentId);

        mockMvc.perform(delete("/api/restaurants/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }


    @Test
    void findByMinRating_shouldReturnBadRequest_whenInvalidMinRating() throws Exception {
        mockMvc.perform(get("/api/restaurants/searchByRating")
                        .param("minRating", "invalid_number")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest());
    }

}
