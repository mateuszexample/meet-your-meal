package pl.molsztyn.meetyourmeal.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.database.entity.RestaurantOwnerEntity;

@Repository
public interface RestaurantOwnerJpaRepository extends JpaRepository<RestaurantOwnerEntity, Integer> {
}
