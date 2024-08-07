package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"name", "price", "description", "image_url"})
@ToString(of = {"foodId", "name", "price", "description", "image_url"})
public class Food {

    Integer foodId;
    String name;
    BigDecimal price;
    String description;
    String image_url;
}
