package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "orderItemId")
@ToString(of = {"orderItemId", "quantity", "orderItemValue"})
public class OrderItem {

    Integer orderItemId;
    Integer quantity;
    BigDecimal orderItemValue;
    Food food;
    Cart cart;
    OrderDetails orderDetails;
}
