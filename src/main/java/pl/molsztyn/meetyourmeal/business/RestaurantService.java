package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.RestaurantDAO;
import pl.molsztyn.meetyourmeal.domain.Restaurant;
import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantOwnerService restaurantOwnerService;
    private final RestaurantDAO restaurantDAO;

    @Transactional
    public void createRestaurant(Restaurant restaurant) {
        RestaurantOwner restaurantOwner = restaurantOwnerService.findRestaurantOwner("94051311769");
        Restaurant restaurantWithRestaurantOwner = restaurant.withRestaurantOwner(restaurantOwner);
        restaurantDAO.createRestaurant(restaurantWithRestaurantOwner);
    }
}
