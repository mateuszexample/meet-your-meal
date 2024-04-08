package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.Restaurant;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.RestaurantEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {

    RestaurantEntity mapToEntity(Restaurant restaurant);

    // CREATING RESTAURANT SAVING RESTAURANT OWNER
    @Mapping(target = "restaurantOwner.restaurant", ignore = true)
    @Mapping(target = "address.restaurant", ignore = true)
    Restaurant mapFromEntity(RestaurantEntity restaurantEntity);
}
