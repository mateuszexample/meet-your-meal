package pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderDetailsEntity;

import java.util.Set;

public interface OrderDetailsJpaRepository extends JpaRepository<OrderDetailsEntity, Integer> {
    Set<OrderDetailsEntity> findByRestaurantRestaurantId(Integer restaurantId);
}
