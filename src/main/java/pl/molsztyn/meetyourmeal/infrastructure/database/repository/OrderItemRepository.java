package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.OrderItemDAO;
import pl.molsztyn.meetyourmeal.domain.OrderItem;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderItemEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.OrderItemJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.OrderItemEntityMapper;

@Repository
@AllArgsConstructor
public class OrderItemRepository implements OrderItemDAO {

    private final OrderItemJpaRepository orderItemJpaRepository;

    private final OrderItemEntityMapper orderItemEntityMapper;

    @Override
    public void createOrderItem(OrderItem orderItem) {
        OrderItemEntity orderItemToSave = orderItemEntityMapper.mapToEntity(orderItem);
        orderItemJpaRepository.saveAndFlush(orderItemToSave);
    }
}
