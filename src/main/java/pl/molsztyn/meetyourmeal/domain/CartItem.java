package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "cartItemId")
@ToString(of = {"cartItemId", "quantity", "cartItemValue"})
public class CartItem {

    Integer cartItemId;
    Integer quantity;
    BigDecimal cartItemValue;
    Food food;
    Cart cart;
}
