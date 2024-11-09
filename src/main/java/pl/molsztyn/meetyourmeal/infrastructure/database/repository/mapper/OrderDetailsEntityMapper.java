package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.OrderDetails;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderDetailsEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDetailsEntityMapper {

    OrderDetailsEntity mapToEntity(OrderDetails orderDetails);

    OrderDetails mapFromEntity(OrderDetailsEntity orderDetailsEntitySaved);
}
