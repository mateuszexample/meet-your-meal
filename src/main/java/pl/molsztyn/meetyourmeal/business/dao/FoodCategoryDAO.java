package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.FoodCategory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FoodCategoryDAO {

    Optional<FoodCategory> findByCategory(String category);

    List<FoodCategory> findByRestaurantsIsEmpty();

    void deleteAllById(Set<Integer> foodCategoriesToDelete);
}
