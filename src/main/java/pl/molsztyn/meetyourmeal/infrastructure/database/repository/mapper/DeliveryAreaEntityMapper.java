package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.DeliveryAreaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryAreaEntityMapper {

    DeliveryAreaEntity mapToEntity(DeliveryArea deliveryArea);

    DeliveryArea mapFromEntity(DeliveryAreaEntity deliveryAreaEntity);
}
