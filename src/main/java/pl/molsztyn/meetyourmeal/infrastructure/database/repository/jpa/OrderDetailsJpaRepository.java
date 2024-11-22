package pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderDetailsEntity;

import java.time.OffsetDateTime;
import java.util.Set;

public interface OrderDetailsJpaRepository extends JpaRepository<OrderDetailsEntity, Integer> {
    Set<OrderDetailsEntity> findByRestaurantRestaurantId(Integer restaurantId);

    Set<OrderDetailsEntity> findByCustomerCustomerId(Integer customerId);

    @Query("SELECT o.toCancelTime FROM OrderDetailsEntity o WHERE o.orderNumber = :orderNumber")
    OffsetDateTime findToCancelTimeByOrderNumber(@Param("orderNumber") String orderNumber);
}
