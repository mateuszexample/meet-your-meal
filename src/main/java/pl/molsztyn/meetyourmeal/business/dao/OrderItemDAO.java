package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.OrderItem;

public interface OrderItemDAO {
    void createOrderItem(OrderItem orderItem);
}
