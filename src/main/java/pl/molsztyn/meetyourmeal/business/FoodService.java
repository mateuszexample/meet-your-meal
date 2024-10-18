package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.FoodCategoryDAO;
import pl.molsztyn.meetyourmeal.business.dao.FoodDAO;
import pl.molsztyn.meetyourmeal.domain.FoodCategory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class FoodService {

    private final FoodCategoryDAO foodCategoryDAO;
    private final FoodDAO foodDAO;

    @Transactional
    public Optional<FoodCategory> findFoodCategory(String foodCategoryToFind) {
        return foodCategoryDAO.findByCategory(foodCategoryToFind);
    }

    @Transactional
    public List<FoodCategory> findUnusedFoodCategories() {
        return foodCategoryDAO.findByRestaurantsIsEmpty();
    }

    @Transactional
    public void deleteFood(Integer foodId) {
        foodDAO.deleteById(foodId);
    }

    @Transactional
    public void deleteAllFoodById(Set<Integer> foodIdSetToDelete) {
        foodDAO.deleteAllById(foodIdSetToDelete);
    }

    @Transactional
    public void deleteAllFoodCategoriesById(Set<Integer> foodCategoriesToDelete) {
        foodCategoryDAO.deleteAllById(foodCategoriesToDelete);
    }
}
