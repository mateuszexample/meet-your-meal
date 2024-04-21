package pl.molsztyn.meetyourmeal.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class FoodCategoryRequest {

    String category;
}
