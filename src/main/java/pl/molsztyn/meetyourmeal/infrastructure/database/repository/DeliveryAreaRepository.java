package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaDTO;
import pl.molsztyn.meetyourmeal.business.dao.DeliveryAreaDAO;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.DeliveryAreaJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.DeliveryAreaEntityMapper;

import java.util.List;
import java.util.Optional;

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
}
