package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "orderItemId")
@ToString(of = {"orderItemId", "foodName", "quantity", "orderItemValue"})
public class OrderItem {

    Integer orderItemId;
    String foodName;
    Integer quantity;
    BigDecimal orderItemValue;
    OrderDetails orderDetails;
}
