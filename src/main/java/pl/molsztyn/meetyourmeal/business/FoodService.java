package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.FoodCategoryDAO;
import pl.molsztyn.meetyourmeal.domain.FoodCategory;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodService {

    private final FoodCategoryDAO foodCategoryDAO;

    @Transactional
    public Optional<FoodCategory> findFoodCategory(String foodCategoryToFind) {
        return foodCategoryDAO.findByCategory(foodCategoryToFind);
    }

    @Transactional
    public List<FoodCategory> findAll() {
        return foodCategoryDAO.findAll();
    }

}
