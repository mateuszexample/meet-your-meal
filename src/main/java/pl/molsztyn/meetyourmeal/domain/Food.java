package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"name", "description", "foodCategory"})
@ToString(of = {"foodId", "name", "price", "description"})
public class Food {

    Integer foodId;
    String name;
    BigDecimal price;
    String description;
    byte[] imageData;
    FoodCategory foodCategory;
    Restaurant restaurant;
}