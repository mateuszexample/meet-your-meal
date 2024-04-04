package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.RestaurantOwnerDAO;
import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.RestaurantOwnerEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.RestaurantOwnerJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.RestaurantOwnerEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RestaurantOwnerRepository implements RestaurantOwnerDAO {

    private final RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;

    private final RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;


    @Override
    public Optional<RestaurantOwner> findByUserId(Integer userId) {
        return restaurantOwnerJpaRepository.findByUserId(userId)
                .map(restaurantOwnerEntityMapper::mapFromEntity);
    }

    @Override
    public void createRestaurant(RestaurantOwner restaurantOwner) {
        RestaurantOwnerEntity restaurantOwnerToSave = restaurantOwnerEntityMapper.mapToEntity(restaurantOwner);
        RestaurantOwnerEntity restaurantOwnerSaved = restaurantOwnerJpaRepository.saveAndFlush(restaurantOwnerToSave);
    }
}
