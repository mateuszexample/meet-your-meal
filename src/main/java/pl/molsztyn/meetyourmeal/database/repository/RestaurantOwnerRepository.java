package pl.molsztyn.meetyourmeal.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.RestaurantOwnerDAO;
import pl.molsztyn.meetyourmeal.database.repository.jpa.RestaurantOwnerJpaRepository;
import pl.molsztyn.meetyourmeal.database.repository.mapper.RestaurantOwnerEntityMapper;
import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RestaurantOwnerRepository implements RestaurantOwnerDAO {

    private final RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;

    private final RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;

    @Override
    public Optional<RestaurantOwner> findByPesel(String pesel) {
        return restaurantOwnerJpaRepository.findByPesel(pesel)
                .map(restaurantOwnerEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<RestaurantOwner> findByUserId(Integer userId) {
        return restaurantOwnerJpaRepository.findByUserId(userId)
                .map(restaurantOwnerEntityMapper::mapFromEntity);
    }
}
