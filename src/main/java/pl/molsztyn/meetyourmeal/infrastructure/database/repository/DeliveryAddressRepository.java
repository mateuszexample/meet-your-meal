package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.DeliveryAddressDAO;
import pl.molsztyn.meetyourmeal.domain.DeliveryAddress;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.DeliveryAddressEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.DeliveryAddressJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class DeliveryAddressRepository implements DeliveryAddressDAO {

    private final DeliveryAddressJpaRepository deliveryAddressJpaRepository;

    private final DeliveryAddressEntityMapper deliveryAddressEntityMapper;

    @Override
    public Optional<DeliveryAddress> findIfExists(DeliveryAddress deliveryAddress) {
        DeliveryAddressEntity deliveryAddressEntity = deliveryAddressEntityMapper.mapToEntity(deliveryAddress);
        Optional<DeliveryAddressEntity> optionalDeliveryAddressEntity = deliveryAddressJpaRepository.findOne(Example.of(deliveryAddressEntity));
        return optionalDeliveryAddressEntity.map(deliveryAddressEntityMapper::mapFromEntity);
    }

    @Override
    public DeliveryAddress create(DeliveryAddress deliveryAddress) {
        DeliveryAddressEntity deliveryAddressEntity = deliveryAddressEntityMapper.mapToEntity(deliveryAddress);
        DeliveryAddressEntity deliveryAddressEntityMerged = deliveryAddressJpaRepository.saveAndFlush(deliveryAddressEntity);
        return deliveryAddressEntityMapper.mapFromEntity(deliveryAddressEntityMerged);
    }
}
