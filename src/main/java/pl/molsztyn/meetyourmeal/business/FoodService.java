package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.molsztyn.meetyourmeal.business.dao.FoodCategoryDAO;
import pl.molsztyn.meetyourmeal.domain.FoodCategory;
import pl.molsztyn.meetyourmeal.domain.FoodCategoryRequest;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodService {

    private final FoodCategoryDAO foodCategoryDAO;

    public Optional<FoodCategory> findFoodCategory(FoodCategoryRequest foodCategoryRequest) {
        return foodCategoryDAO.findByCategory(foodCategoryRequest.getCategory());
    }
}
