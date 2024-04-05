package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaDTO;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;

import java.util.List;
import java.util.Optional;

public interface DeliveryAreaDAO {

    Optional<DeliveryArea> findByCountryAndCityAndPostalCodeAndStreet(
            String country,
            String city,
            String postalCode,
            String street
    );

    List<DeliveryArea> findAll();
}
