package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.DeliveryAddress;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.DeliveryAddressEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryAddressEntityMapper {
    DeliveryAddressEntity mapToEntity(DeliveryAddress deliveryAddress);

    DeliveryAddress mapFromEntity(DeliveryAddressEntity deliveryAddressEntity);
}
