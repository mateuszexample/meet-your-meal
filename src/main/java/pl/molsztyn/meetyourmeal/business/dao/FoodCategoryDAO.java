package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.FoodCategory;

import java.util.Optional;

public interface FoodCategoryDAO {

    Optional<FoodCategory> findByCategory(String category);
}
