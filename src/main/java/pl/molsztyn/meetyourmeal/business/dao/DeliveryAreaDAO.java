package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaDTO;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DeliveryAreaDAO {

    Optional<DeliveryArea> findByCountryAndCityAndPostalCodeAndStreet(
            String country,
            String city,
            String postalCode,
            String street
    );

    List<DeliveryArea> findAll();

    Set<String> findCitiesByCountry(String country);

    Set<String> findPostalCodesByCountryAndCity(String country, String city);

    Set<String> findStreetsByCountryAndCityAndPostalCode(String country, String city, String postalCode);
}
