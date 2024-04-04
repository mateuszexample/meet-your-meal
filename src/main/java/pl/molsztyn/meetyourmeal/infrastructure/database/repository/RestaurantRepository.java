package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.RestaurantDAO;
import pl.molsztyn.meetyourmeal.domain.Restaurant;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.RestaurantEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.RestaurantEntityMapper;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantToSave = restaurantEntityMapper.mapToEntity(restaurant);
        RestaurantEntity restaurantSaved = restaurantJpaRepository.saveAndFlush(restaurantToSave);
    }

}
