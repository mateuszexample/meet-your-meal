package pl.molsztyn.meetyourmeal.domain;

import lombok.*;
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
}


