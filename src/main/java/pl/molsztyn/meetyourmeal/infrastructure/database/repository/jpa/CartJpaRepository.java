package pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartEntity;

@Repository
public interface CartJpaRepository extends JpaRepository<CartEntity, Integer> {
}
