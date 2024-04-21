package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"category"})
@ToString(of = {"foodCategoryId", "category"})
public class FoodCategory {

    Integer foodCategoryId;
    String category;
}
