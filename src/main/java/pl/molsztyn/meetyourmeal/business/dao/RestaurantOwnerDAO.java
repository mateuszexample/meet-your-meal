package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;

import java.util.Optional;

public interface RestaurantOwnerDAO {

    Optional<RestaurantOwner> findByPesel(String pesel);
}
