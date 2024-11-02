package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.OrderItem;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderItemEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemEntityMapper {
    OrderItemEntity mapToEntity(OrderItem orderItem);
}
