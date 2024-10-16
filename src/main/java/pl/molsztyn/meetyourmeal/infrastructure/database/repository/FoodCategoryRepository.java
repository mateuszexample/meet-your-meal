package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.FoodCategoryDAO;
import pl.molsztyn.meetyourmeal.domain.FoodCategory;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.FoodCategoryEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.FoodCategoryJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.FoodCategoryEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class FoodCategoryRepository implements FoodCategoryDAO {

    private final FoodCategoryJpaRepository foodCategoryJpaRepository;

    private final FoodCategoryEntityMapper foodCategoryEntityMapper;

    @Override
    public Optional<FoodCategory> findByCategory(String category) {
        return foodCategoryJpaRepository.findByCategory(category)
                .map(foodCategoryEntityMapper::mapFromEntity);
    }

    @Override
    public List<FoodCategory> findByRestaurantsIsEmpty() {
        return foodCategoryJpaRepository.findByRestaurantsIsEmpty().stream()
                .map(foodCategoryEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void deleteAll(List<FoodCategory> unusedFoodCategories) {
        List<FoodCategoryEntity> categoriesToDelete = unusedFoodCategories.stream()
                .map(foodCategoryEntityMapper::mapToEntity)
                .toList();
        foodCategoryJpaRepository.deleteAll(categoriesToDelete);
    }
}
