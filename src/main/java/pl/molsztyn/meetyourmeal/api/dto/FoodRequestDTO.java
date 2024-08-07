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
public class FoodRequestDTO {

    private String category;
    private String name;
    private String description;
    private BigDecimal price;
    private String image_url;
}
