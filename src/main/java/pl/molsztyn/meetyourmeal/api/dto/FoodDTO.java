package pl.molsztyn.meetyourmeal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {

    private Integer foodId;
    private String name;
    private BigDecimal price;
    private String description;
    private String image_url;
}
