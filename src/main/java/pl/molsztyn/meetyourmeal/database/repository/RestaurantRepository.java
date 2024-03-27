package pl.molsztyn.meetyourmeal.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.RestaurantDAO;
import pl.molsztyn.meetyourmeal.database.entity.RestaurantEntity;
import pl.molsztyn.meetyourmeal.database.repository.jpa.RestaurantJpaRepository;
import pl.molsztyn.meetyourmeal.database.repository.mapper.RestaurantEntityMapper;
import pl.molsztyn.meetyourmeal.domain.Restaurant;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;

    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void createRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantToSave = restaurantEntityMapper.mapToEntity(restaurant);
        RestaurantEntity restaurantSaved = restaurantJpaRepository.saveAndFlush(restaurantToSave);
    }
}
