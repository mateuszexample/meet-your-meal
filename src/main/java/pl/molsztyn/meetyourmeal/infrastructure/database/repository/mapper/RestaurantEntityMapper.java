package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.Food;
import pl.molsztyn.meetyourmeal.domain.Restaurant;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.FoodEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.RestaurantEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {

    @Mapping(target = "restaurantOwner.restaurant", ignore = true)
    @Mapping(target = "address.restaurant", ignore = true)
    @Mapping(source = "menu", target = "menu", qualifiedByName = "mapMenuFromEntity")
    Restaurant mapFromEntity(RestaurantEntity restaurantEntity);

    @Named("mapMenuFromEntity")
    default Set<Food> mapMenuFromEntity(Set<FoodEntity> foodEntities) {
        return foodEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "restaurant.menu", ignore = true)
    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(target = "restaurant.restaurantOwner", ignore = true)
    @Mapping(target = "restaurant.deliveryAreas", ignore = true)
    @Mapping(target = "restaurant.foodCategories", ignore = true)
    Food mapFromEntity(FoodEntity foodEntity);

    @Mapping(target = "address.restaurant", ignore = true)
    @Mapping(source = "menu", target = "menu", qualifiedByName = "mapMenuToEntity")
    RestaurantEntity mapToEntity(Restaurant restaurant);

    @Named("mapMenuToEntity")
    default Set<FoodEntity> mapMenuToEntity(Set<Food> menu) {
        return menu.stream().map(this::mapToEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "restaurant.menu", ignore = true)
    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(target = "restaurant.restaurantOwner", ignore = true)
    @Mapping(target = "restaurant.deliveryAreas", ignore = true)
    @Mapping(target = "restaurant.foodCategories", ignore = true)
    FoodEntity mapToEntity(Food food);
}
