package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@With
@Value
@Builder
@EqualsAndHashCode(of = "cartId")
@ToString(of = {"cartId", "totalPrice", "customer", "restaurant"})
public class Cart {

    Integer cartId;
    BigDecimal totalPrice;
    Customer customer;
    Restaurant restaurant;
    List<OrderItem> orderItems;

    public List<OrderItem> getOrderItems() {
        return Objects.isNull(orderItems) ? new ArrayList<>() : orderItems;
    }
}
