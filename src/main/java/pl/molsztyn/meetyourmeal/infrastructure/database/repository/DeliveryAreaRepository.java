package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.DeliveryAreaDAO;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.DeliveryAreaEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.DeliveryAreaJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.DeliveryAreaEntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class DeliveryAreaRepository implements DeliveryAreaDAO {

    private final DeliveryAreaJpaRepository deliveryAreaJpaRepository;

    private final DeliveryAreaEntityMapper deliveryAreaEntityMapper;

    @Override
    public Optional<DeliveryArea> findByCountryAndCityAndPostalCodeAndStreet(
            String country,
            String city,
            String postalCode,
            String street
    ) {
        return deliveryAreaJpaRepository.findByCountryAndCityAndPostalCodeAndStreet(
                country,
                city,
                postalCode,
                street
        ).map(deliveryAreaEntityMapper::mapFromEntity);
    }

    @Override
    public List<DeliveryArea> findAll() {
        return deliveryAreaJpaRepository.findAll().stream()
                .map(deliveryAreaEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Set<String> findCitiesByCountry(String country) {
        Set<DeliveryAreaEntity> citiesByCountry = deliveryAreaJpaRepository.findCitiesByCountry(country);

        return citiesByCountry.stream()
                .map(deliveryAreaEntityMapper::mapFromEntity)
                .map(DeliveryArea::getCity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> findPostalCodesByCountryAndCity(String country, String city) {
        var postalCodesByCountryAndCity = deliveryAreaJpaRepository.findPostalCodesByCountryAndCity(country, city);

        return postalCodesByCountryAndCity.stream()
                .map(deliveryAreaEntityMapper::mapFromEntity)
                .map(DeliveryArea::getPostalCode)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> findStreetsByCountryAndCityAndPostalCode(String country, String city, String postalCode) {
        var streetsByCountryAndCityAndPostalCode = deliveryAreaJpaRepository.findStreetsByCountryAndCityAndPostalCode(
                country,
                city,
                postalCode);

        return streetsByCountryAndCityAndPostalCode.stream()
                .map(deliveryAreaEntityMapper::mapFromEntity)
                .map(DeliveryArea::getStreet)
                .collect(Collectors.toSet());
    }

    @Override
    public List<DeliveryArea> findByRestaurantsIsEmpty() {
        return deliveryAreaJpaRepository.findByRestaurantsIsEmpty().stream()
                .map(deliveryAreaEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void deleteAll(List<DeliveryArea> unusedDeliveryAreas) {
        List<DeliveryAreaEntity> deliveryAreasToDelete = unusedDeliveryAreas.stream()
                .map(deliveryAreaEntityMapper::mapToEntity)
                .toList();
        deliveryAreaJpaRepository.deleteAll(deliveryAreasToDelete);
    }
}
