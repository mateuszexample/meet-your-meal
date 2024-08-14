package pl.molsztyn.meetyourmeal.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@With
@Value
@Builder
public class FoodRequest {

    String category;
    String name;
    BigDecimal price;
    String description;
    byte[] imageData;
}
