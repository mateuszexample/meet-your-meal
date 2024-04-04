package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.DeliveryArea;

import java.util.Optional;

public interface DeliveryAreaDAO {

    Optional<DeliveryArea> findByCountryAndCityAndPostalCodeAndStreet(
            String country,
            String city,
            String postalCode,
            String street
    );
}
