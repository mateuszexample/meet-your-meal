package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.OrderDetails;

import java.util.Set;

public interface OrderDetailsDAO {
    OrderDetails create(OrderDetails orderDetails);

    Set<OrderDetails> findAllByRestaurantId(Integer restaurantId);
}
