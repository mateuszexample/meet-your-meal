package pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartItemEntity;

public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Integer> {
}
