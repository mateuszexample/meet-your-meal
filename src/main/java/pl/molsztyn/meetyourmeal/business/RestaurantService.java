package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.molsztyn.meetyourmeal.business.dao.RestaurantDAO;
import pl.molsztyn.meetyourmeal.domain.Restaurant;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;

    public void updateRestaurant(Restaurant restaurant) {
        restaurantDAO.updateRestaurant(restaurant);
    }

    public List<Restaurant> getRestaurantsByDeliveryAreaId(Integer deliveryAreaId) {
        return restaurantDAO.findRestaurantsByDeliveryAreaId(deliveryAreaId);
    }
}
