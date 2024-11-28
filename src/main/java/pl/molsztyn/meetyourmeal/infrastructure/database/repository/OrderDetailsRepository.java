package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.OrderDetailsDAO;
import pl.molsztyn.meetyourmeal.domain.OrderDetails;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderDetailsEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.OrderDetailsJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.OrderDetailsEntityMapper;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class OrderDetailsRepository implements OrderDetailsDAO {

    private final OrderDetailsJpaRepository orderDetailsJpaRepository;

    private final OrderDetailsEntityMapper orderDetailsEntityMapper;

    @Override
    public OrderDetails create(OrderDetails orderDetails) {
        OrderDetailsEntity orderToSave = orderDetailsEntityMapper.mapToEntity(orderDetails);
        OrderDetailsEntity orderSaved = orderDetailsJpaRepository.saveAndFlush(orderToSave);
        return orderDetailsEntityMapper.mapFromEntity(orderSaved);
    }

    @Override
    public Set<OrderDetails> findAllByRestaurantId(Integer restaurantId) {
        Set<OrderDetailsEntity> orderDetailsEntities = orderDetailsJpaRepository.findByRestaurantRestaurantId(restaurantId);

        return orderDetailsEntities.stream()
                .map(orderDetailsEntityMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDetails> findAllByCustomerId(Integer customerId) {
        Set<OrderDetailsEntity> orderDetailsEntities = orderDetailsJpaRepository.findByCustomerCustomerId(customerId);

        return orderDetailsEntities.stream()
                .map(orderDetailsEntityMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public OffsetDateTime findToCancelTime(String orderNumber) {
        return orderDetailsJpaRepository.findToCancelTimeByOrderNumber(orderNumber);
    }
}
