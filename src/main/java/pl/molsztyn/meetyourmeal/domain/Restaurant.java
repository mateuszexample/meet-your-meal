package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "restaurantId")
@ToString(of = {"restaurantId", "name"})
public class Restaurant {

    Integer restaurantId;
    String name;
    Address address;
    RestaurantOwner restaurantOwner;
    Set<DeliveryArea> deliveryAreas;
    Set<FoodCategory> foodCategories;
    Set<Food> menu;

    public Set<DeliveryArea> getDeliveryAreas() {
        return Objects.isNull(deliveryAreas) ? new HashSet<>() : deliveryAreas;
    }
    public Set<FoodCategory> getFoodCategories() {
        return Objects.isNull(foodCategories) ? new HashSet<>() : foodCategories;
    }  public Set<Food> getMenu() {
        return Objects.isNull(menu) ? new HashSet<>() : menu;
    }
}


