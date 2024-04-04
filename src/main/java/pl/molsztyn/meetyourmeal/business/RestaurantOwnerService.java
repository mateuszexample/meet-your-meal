package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.RestaurantOwnerDAO;
import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantOwnerService {

    private final RestaurantOwnerDAO restaurantOwnerDAO;

    @Transactional
    public RestaurantOwner findRestaurantOwner(Integer userId) {
        Optional<RestaurantOwner> restaurantOwner = restaurantOwnerDAO.findByUserId(userId);
        if (restaurantOwner.isEmpty()) {
            throw new NotFoundException("Could not find restaurant owner by user id: [%s]".formatted(userId));
        }
        return restaurantOwner.get();
    }


    @Transactional
    public void createRestaurant(RestaurantOwner restaurantOwnerWithRestaurant) {
        restaurantOwnerDAO.createRestaurant(restaurantOwnerWithRestaurant);
    }
}
