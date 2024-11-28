package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.DeliveryAddress;

import java.util.Optional;

public interface DeliveryAddressDAO {
    Optional<DeliveryAddress> findIfExists(DeliveryAddress deliveryAddress);
    DeliveryAddress create(DeliveryAddress deliveryAddress);
}
