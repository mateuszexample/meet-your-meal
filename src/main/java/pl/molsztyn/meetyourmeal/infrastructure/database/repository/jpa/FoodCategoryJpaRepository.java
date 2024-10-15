package pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.FoodCategoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodCategoryJpaRepository extends JpaRepository<FoodCategoryEntity, Integer> {

    Optional<FoodCategoryEntity> findByCategory(String category);
    List<FoodCategoryEntity> findByRestaurantsIsEmpty();
}
