package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantCreateRequestDTO;
import pl.molsztyn.meetyourmeal.domain.Restaurant;

@Mapper(componentModel = "spring")
public interface RestaurantCreateRequestMapper {
    Restaurant map(RestaurantCreateRequestDTO dto);
}
