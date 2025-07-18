package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.request.RatingRequestDTO;
import com.example.restaurantrating.dto.response.RatingResponseDTO;
import com.example.restaurantrating.exception.DuplicateReviewException;
import com.example.restaurantrating.service.RatingService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void save_shouldReturnOk() throws Exception {
        RatingRequestDTO dto = RatingRequestDTO.builder()
                .visitorId(1L)
                .restaurantId(2L)
                .score(4)
                .review("Good place")
                .build();

        mockMvc.perform(post("/api/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        Mockito.verify(ratingService).save(any(RatingRequestDTO.class));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        Long visitorId = 1L;
        Long restaurantId = 2L;

        mockMvc.perform(delete("/api/ratings/{visitorId}/{restaurantId}", visitorId, restaurantId))
                .andExpect(status().isOk());

        Mockito.verify(ratingService).remove(visitorId, restaurantId);
    }

    @Test
    void findAll_shouldReturnPagedResponse() throws Exception {
        RatingResponseDTO dto = new RatingResponseDTO(1L, 2L, 5, "Excellent");

        Page<RatingResponseDTO> page = new PageImpl<>(List.of(dto));

        Mockito.when(ratingService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/ratings")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "score")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].visitorId").value(dto.visitorId()))
                .andExpect(jsonPath("$.content[0].restaurantId").value(dto.restaurantId()))
                .andExpect(jsonPath("$.content[0].score").value(dto.score()))
                .andExpect(jsonPath("$.content[0].review").value(dto.review()));

        Mockito.verify(ratingService).findAll(any(Pageable.class));
    }

    @Test
    void save_shouldReturnBadRequest_whenInvalidData() throws Exception {
        RatingRequestDTO invalidDto = RatingRequestDTO.builder()
                .visitorId(null)
                .restaurantId(2L)
                .score(10)
                .review("Very long...")
                .build();

        mockMvc.perform(post("/api/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void save_shouldReturnConflict_whenDuplicateReview() throws Exception {
        RatingRequestDTO dto = RatingRequestDTO.builder()
                .visitorId(1L)
                .restaurantId(2L)
                .score(4)
                .review("Already reviewed")
                .build();

        Mockito.doThrow(new DuplicateReviewException("Review already exists"))
                .when(ratingService).save(any());

        mockMvc.perform(post("/api/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    void findAll_shouldReturnEmptyPage_whenNoRatingsExist() throws Exception {
        Page<RatingResponseDTO> emptyPage = new PageImpl<>(List.of());

        Mockito.when(ratingService.findAll(any(Pageable.class))).thenReturn(emptyPage);

        mockMvc.perform(get("/api/ratings")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "score")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

}
