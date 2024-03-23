package pl.molsztyn.meetyourmeal.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.database.entity.RestaurantEntity;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Integer> {
}
