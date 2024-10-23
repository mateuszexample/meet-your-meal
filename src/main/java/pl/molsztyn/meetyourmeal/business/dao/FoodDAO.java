package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.Food;

import java.util.Set;

public interface FoodDAO {
    void deleteById(Integer foodId);

    void deleteAllById(Set<Integer> foodIdSetToDelete);

    Food findFoodById(Integer id);
}
