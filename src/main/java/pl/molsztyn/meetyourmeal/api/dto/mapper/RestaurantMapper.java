package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantCreateRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantDTO;
import pl.molsztyn.meetyourmeal.domain.Restaurant;
import pl.molsztyn.meetyourmeal.domain.RestaurantCreateRequest;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "imageData", ignore = true)
    RestaurantCreateRequest mapFromDTO(RestaurantCreateRequestDTO dto);

    default RestaurantDTO mapToDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .name(restaurant.getName())
                .restaurantCountry(restaurant.getAddress().getCountry())
                .restaurantCity(restaurant.getAddress().getCity())
                .restaurantPostalCode(restaurant.getAddress().getPostalCode())
                .restaurantStreet(restaurant.getAddress().getStreet())
                .restaurantBuildingNumber(restaurant.getAddress().getBuildingNumber())
                .restaurantUnitNumber(restaurant.getAddress().getUnitNumber())
                .imageData(restaurant.getImageData())
                .hasImage(restaurant.getImageData() != null && restaurant.getImageData().length > 0)
                .build();
    }
}
