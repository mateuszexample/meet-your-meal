package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.OrderDetails;

public interface OrderDetailsDAO {
    OrderDetails create(OrderDetails orderDetails);
}
