package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantCreateRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantDTO;
import pl.molsztyn.meetyourmeal.domain.Restaurant;
import pl.molsztyn.meetyourmeal.domain.RestaurantCreateRequest;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "imageData", ignore = true)
    RestaurantCreateRequest mapFromDTO(RestaurantCreateRequestDTO dto);

    default RestaurantDTO mapToDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .name(restaurant.getName())
                .hasImage(restaurant.getImageData() != null && restaurant.getImageData().length > 0)
                .build();
    }
}
