package pl.molsztyn.meetyourmeal.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.database.entity.RestaurantOwnerEntity;
import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantOwnerEntityMapper {

    @Mapping(target = "restaurant", ignore = true)
    RestaurantOwner mapFromEntity(RestaurantOwnerEntity restaurantOwnerEntity);
}
