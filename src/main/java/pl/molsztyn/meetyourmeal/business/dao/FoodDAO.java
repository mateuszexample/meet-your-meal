package pl.molsztyn.meetyourmeal.business.dao;

import java.util.Set;

public interface FoodDAO {
    void deleteById(Integer foodId);

    void deleteAllById(Set<Integer> foodIdSetToDelete);
}
