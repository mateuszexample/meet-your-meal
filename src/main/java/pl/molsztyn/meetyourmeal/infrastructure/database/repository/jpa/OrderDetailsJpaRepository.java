package pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderDetailsEntity;

public interface OrderDetailsJpaRepository extends JpaRepository<OrderDetailsEntity, Integer> {
}
