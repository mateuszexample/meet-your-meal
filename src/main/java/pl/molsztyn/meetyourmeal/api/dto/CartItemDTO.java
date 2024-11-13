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
public class CartItemDTO {

    private Integer quantity;
    private BigDecimal cartItemValue;
    private Integer foodId;
    private String foodName;
}
