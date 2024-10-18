package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.FoodDAO;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.FoodJpaRepository;

import java.util.Set;

@Repository
@AllArgsConstructor
public class FoodRepository implements FoodDAO {

    private final FoodJpaRepository foodJpaRepository;

    @Override
    public void deleteById(Integer foodId) {
        foodJpaRepository.deleteById(foodId);
    }

    @Override
    public void deleteAllById(Set<Integer> foodIdSetToDelete) {
        foodJpaRepository.deleteAllById(foodIdSetToDelete);
    }
}
