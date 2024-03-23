package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "restaurantOwnerId")
@ToString(of = {"restaurantOwnerId", "name", "surname", "pesel"})
public class RestaurantOwner {

    Integer restaurantOwnerId;
    String name;
    String surname;
    String pesel;
    Restaurant restaurant;
}
