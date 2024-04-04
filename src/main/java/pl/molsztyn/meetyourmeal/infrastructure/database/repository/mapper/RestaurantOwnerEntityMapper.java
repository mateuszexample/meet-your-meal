package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.RestaurantOwnerEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantOwnerEntityMapper {

    @Mapping(target = "restaurant.restaurantOwner", ignore = true)
    @Mapping(target = "restaurant.address.restaurant", ignore = true)
    RestaurantOwner mapFromEntity(RestaurantOwnerEntity restaurantOwnerEntity);

    RestaurantOwnerEntity mapToEntity(RestaurantOwner restaurantOwner);
}
