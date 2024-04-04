package pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.DeliveryAreaEntity;

import java.util.Optional;

@Repository
public interface DeliveryAreaJpaRepository extends JpaRepository<DeliveryAreaEntity, Integer> {
    Optional<DeliveryAreaEntity> findByCountryAndCityAndPostalCodeAndStreet(
            String country,
            String city,
            String postalCode,
            String street
    );
}
