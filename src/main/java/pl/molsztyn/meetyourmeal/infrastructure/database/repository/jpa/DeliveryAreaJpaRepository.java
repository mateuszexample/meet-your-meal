package pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.DeliveryAreaEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DeliveryAreaJpaRepository extends JpaRepository<DeliveryAreaEntity, Integer> {
    Optional<DeliveryAreaEntity> findByCountryAndCityAndPostalCodeAndStreet(
            String country,
            String city,
            String postalCode,
            String street
    );

    Set<DeliveryAreaEntity> findCitiesByCountry(String country);

    Set<DeliveryAreaEntity> findPostalCodesByCountryAndCity(String country, String city);

    Set<DeliveryAreaEntity> findStreetsByCountryAndCityAndPostalCode(String country, String city, String postalCode);

    List<DeliveryAreaEntity> findByRestaurantsIsEmpty();
}
