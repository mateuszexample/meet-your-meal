package pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.FoodEntity;

@Repository
public interface FoodJpaRepository extends JpaRepository<FoodEntity, Integer> {

}
