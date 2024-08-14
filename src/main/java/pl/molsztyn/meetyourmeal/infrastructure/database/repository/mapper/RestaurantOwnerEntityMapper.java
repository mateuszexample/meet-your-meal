package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.Food;
import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.FoodEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.RestaurantOwnerEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantOwnerEntityMapper {

    @Mapping(target = "restaurant.restaurantOwner", ignore = true)
    @Mapping(target = "restaurant.address.restaurant", ignore = true)
    @Mapping(source = "restaurant.menu", target = "restaurant.menu", qualifiedByName = "mapMenu")
    RestaurantOwner mapFromEntity(RestaurantOwnerEntity restaurantOwnerEntity);

    RestaurantOwnerEntity mapToEntity(RestaurantOwner restaurantOwner);

    @Named("mapMenu")
    default Set<Food> mapMenu(Set<FoodEntity> foodEntities) {
        return foodEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "restaurant.menu", ignore = true)
    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(target = "restaurant.restaurantOwner", ignore = true)
    @Mapping(target = "restaurant.deliveryAreas", ignore = true)
    @Mapping(target = "restaurant.foodCategories", ignore = true)
    Food mapFromEntity(FoodEntity foodEntity);
}
