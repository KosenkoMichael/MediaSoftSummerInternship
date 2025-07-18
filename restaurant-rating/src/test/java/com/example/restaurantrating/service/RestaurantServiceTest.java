package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.request.RestaurantRequestDTO;
import com.example.restaurantrating.dto.response.RestaurantResponseDTO;
import com.example.restaurantrating.entity.KitchenType;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.mapper.RestaurantMapper;
import com.example.restaurantrating.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void save_shouldCallRepositoryWithMappedEntity() {
        RestaurantRequestDTO dto = RestaurantRequestDTO.builder()
                .name("Test Restaurant")
                .description("Test Description")
                .kitchenType(KitchenType.ITALIAN)
                .averageCheck(new BigDecimal("25.50"))
                .build();

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setDescription("Test Description");
        restaurant.setKitchentype(KitchenType.ITALIAN);
        restaurant.setAverageCheck(new BigDecimal("25.50"));
        restaurant.setAverageRating(BigDecimal.ZERO);

        when(restaurantMapper.toEntity(dto)).thenReturn(restaurant);

        restaurantService.save(dto);

        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void remove_shouldCallDelete() {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();  // или можно задать id, если нужно

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));

        restaurantService.remove(id);

        verify(restaurantRepository).delete(restaurant);
    }

    @Test
    void findAll_shouldReturnListOfRestaurantResponseDTOs() {
        Restaurant r1 = new Restaurant(1L, "Name1", "Desc1", null, null, BigDecimal.valueOf(4.5));
        Restaurant r2 = new Restaurant(2L, "Name2", "Desc2", null, null, BigDecimal.valueOf(3.7));

        RestaurantResponseDTO dto1 = new RestaurantResponseDTO(1L, "Name1", "Desc1", null, null, BigDecimal.valueOf(4.5));
        RestaurantResponseDTO dto2 = new RestaurantResponseDTO(2L, "Name2", "Desc2", null, null, BigDecimal.valueOf(3.7));

        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(r1, r2));
        when(restaurantMapper.toResponseDTO(r1)).thenReturn(dto1);
        when(restaurantMapper.toResponseDTO(r2)).thenReturn(dto2);

        List<RestaurantResponseDTO> result = restaurantService.findAll();

        assertThat(result).containsExactly(dto1, dto2);
    }

    @Test
    void findAll_withPageable_shouldReturnPageOfRestaurantResponseDTOs() {
        Restaurant r = new Restaurant(1L, "Name", "Desc", null, null, BigDecimal.valueOf(4.0));
        RestaurantResponseDTO dto = new RestaurantResponseDTO(1L, "Name", "Desc", null, null, BigDecimal.valueOf(4.0));

        Page<Restaurant> restaurantPage = new PageImpl<>(List.of(r));
        Pageable pageable = PageRequest.of(0, 10);

        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantPage);
        when(restaurantMapper.toResponseDTO(r)).thenReturn(dto);

        Page<RestaurantResponseDTO> result = restaurantService.findAll(pageable);

        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void findById_shouldReturnRestaurantResponseDTO() {
        Restaurant restaurant = new Restaurant(1L, "Name", "Desc", null, null, BigDecimal.valueOf(4.0));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        // Добавляем мок для маппера, иначе будет null
        RestaurantResponseDTO dto = new RestaurantResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getKitchentype(),
                restaurant.getAverageCheck(),
                restaurant.getAverageRating()
        );
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(dto);

        RestaurantResponseDTO result = restaurantService.findById(1L);

        assertThat(result.id()).isEqualTo(restaurant.getId());
        assertThat(result.name()).isEqualTo(restaurant.getName());
        assertThat(result.description()).isEqualTo(restaurant.getDescription());
        assertThat(result.kitchenType()).isEqualTo(restaurant.getKitchentype());
        assertThat(result.averageCheck()).isEqualTo(restaurant.getAverageCheck());
        assertThat(result.averageRating()).isEqualTo(restaurant.getAverageRating());
    }

    @Test
    void findByMinRating_shouldReturnPageOfRestaurantResponseDTOs() {
        BigDecimal minRating = BigDecimal.valueOf(3.5);
        Pageable pageable = PageRequest.of(0, 10);
        Restaurant r = new Restaurant(1L, "Name", "Desc", null, null, BigDecimal.valueOf(4.0));
        RestaurantResponseDTO dto = new RestaurantResponseDTO(1L, "Name", "Desc", null, null, BigDecimal.valueOf(4.0));

        Page<Restaurant> page = new PageImpl<>(List.of(r));

        when(restaurantRepository.findByAverageRatingGreaterThanEqual(minRating, pageable)).thenReturn(page);
        when(restaurantMapper.toResponseDTO(r)).thenReturn(dto);

        Page<RestaurantResponseDTO> result = restaurantService.findByMinRating(minRating, pageable);

        assertThat(result.getContent()).containsExactly(dto);
    }
}
