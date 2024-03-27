package pl.molsztyn.meetyourmeal.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.database.entity.RestaurantEntity;
import pl.molsztyn.meetyourmeal.domain.Restaurant;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {
    RestaurantEntity mapToEntity(Restaurant restaurant);
}
