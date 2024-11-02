package pl.molsztyn.meetyourmeal.domain;

import lombok.*;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "orderDetailsId")
@ToString(of = {"orderDetailsId", "orderNumber", "receivedTime", "toCancelTime"})
public class OrderDetails {

    Integer orderDetailsId;
    String orderNumber;
    OffsetDateTime receivedTime;
    OffsetDateTime toCancelTime;
    BigDecimal orderValue;
    OrderStatus orderStatus;
    Customer customer;
    Restaurant restaurant;
    Set<OrderItem> orderItems;

    public Set<OrderItem> getOrderItems() {
        return Objects.isNull(orderItems) ? new HashSet<>() : orderItems;
    }
}
