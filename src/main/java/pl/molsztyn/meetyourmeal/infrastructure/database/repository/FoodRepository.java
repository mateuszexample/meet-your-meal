package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.FoodDAO;
import pl.molsztyn.meetyourmeal.domain.Food;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.FoodEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.FoodJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.FoodEntityMapper;

import java.util.Set;

@Repository
@AllArgsConstructor
public class FoodRepository implements FoodDAO {

    private final FoodJpaRepository foodJpaRepository;
    private final FoodEntityMapper foodEntityMapper;

    @Override
    public void deleteById(Integer foodId) {
        foodJpaRepository.deleteById(foodId);
    }

    @Override
    public void deleteAllById(Set<Integer> foodIdSetToDelete) {
        foodJpaRepository.deleteAllById(foodIdSetToDelete);
    }

    @Override
    public Food findFoodById(Integer id) {
        FoodEntity foodEntity = foodJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find food by id: [%s]".formatted(id)));
        return foodEntityMapper.mapFromEntity(foodEntity);
    }
}
