package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    Set<OrderItem> orderItems;

    public Set<OrderItem> getOrderItems() {
        return Objects.isNull(orderItems) ? new HashSet<>() : orderItems;
    }
}
