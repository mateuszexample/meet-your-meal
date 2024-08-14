package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.RestaurantDAO;
import pl.molsztyn.meetyourmeal.domain.Restaurant;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;

    @Transactional
    public void updateRestaurant(Restaurant restaurant) {
        restaurantDAO.updateRestaurant(restaurant);
    }

    public List<Restaurant> getRestaurantsByDeliveryAreaId(Integer deliveryAreaId) {
        return restaurantDAO.findRestaurantsByDeliveryAreaId(deliveryAreaId);
    }

    public Restaurant getRestaurantByName(String restaurantName) {
        return restaurantDAO.findRestaurantByName(restaurantName);
    }
}
