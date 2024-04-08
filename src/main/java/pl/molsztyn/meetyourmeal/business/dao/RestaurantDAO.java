package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.Restaurant;

import java.util.List;

public interface RestaurantDAO {

    void updateRestaurant(Restaurant restaurant);

    List<Restaurant> findRestaurantsByDeliveryAreaId(Integer deliveryAreaId);

    Restaurant findRestaurantByName(String restaurantName);
}
