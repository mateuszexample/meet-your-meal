package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantCreateRequestDTO;
import pl.molsztyn.meetyourmeal.domain.RestaurantCreateRequest;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantCreateRequest mapFromDTO(RestaurantCreateRequestDTO dto);
}
